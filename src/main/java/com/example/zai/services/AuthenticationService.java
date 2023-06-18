package com.example.zai.services;

import com.example.zai.enums.OrderStatus;
import com.example.zai.enums.Roles;
import com.example.zai.models.Order;
import com.example.zai.models.User;
import com.example.zai.repositories.OrderRepository;
import com.example.zai.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final OrderRepository orderRepository;

    public void register(String username, String password, String email) {

        System.out.println("AuthenticationService.register");
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("User with email " + email + " already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Roles.ROLE_USER);
        userRepository.save(user);
        Order cart = Order.builder()
                .user(user)
                .status(OrderStatus.SHOPPING_CART)
                .orderProductsList(new ArrayList<>())
                .build();
        Order wishlist = Order.builder()
                .user(user)
                .status(OrderStatus.WHISHLIST)
                .orderProductsList(new ArrayList<>())
                .build();

        orderRepository.save(cart);
        orderRepository.save(wishlist);

    }

    public void logIn(HttpServletRequest request, String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Wrong password");
        }else{

            List<GrantedAuthority> authorities = Stream.of(user.getRole()).map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username,password,authorities);

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);

        }
    }

    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            System.out.println("Authentication is null");
        }else{
            System.out.println("Authentication is not null " + authentication.getName());
        }
        assert authentication != null;
        String username = authentication.getPrincipal().toString();
        return userRepository.findByUsername(username);
    }

    public void changeUserDetails(HttpServletRequest request, String newUsername, String password, String newMail) {
        System.out.println("Changing user details");
        User user = userRepository.findByUsername(newUsername);
        if(user != null){
            throw new RuntimeException("Username taken");
        }
        String correctPassword = getLoggedUser().getPassword();
        if(!passwordEncoder.matches(password, correctPassword)){
            throw new RuntimeException("Wrong password");
        }
        user = getLoggedUser();
        user.setUsername(newUsername);
        user.setEmail(newMail);
        userRepository.save(user);
        System.out.println("User details changed");

        createSession(request, user);
    }

    private void createSession(HttpServletRequest request, User user) {
        List<GrantedAuthority> authorities = Stream.of(user.getRole()).map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),authorities);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);
    }

    public void changeUserPassword(HttpServletRequest request, String oldPassword, String newPassword) {
        User user = getLoggedUser();
        String correctPassword = user.getPassword();
        if(!passwordEncoder.matches(oldPassword, correctPassword)){
            throw new RuntimeException("Wrong password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        createSession(request, user);
    }



}
