package com.example.zai.controllers;

import com.example.zai.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public String signUp(HttpServletRequest request,@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        if(username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            error = "All fields must be filled";
            return "redirect:redirect";
        }

        if(username.length()<6) {
            error = "Username must be at least 6 characters long";
            return "redirect:redirect";
        }

        authenticationService.register(username, password, email);
        authenticationService.logIn(request,username, password);
        return "redirect:/products/shop";
    }

    String error = "";
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirectToPage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alertMessage", error);
        return "redirect:/user/myAccount";
    }



    @PostMapping("/signin")
    public String signIn(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {

        try {
            authenticationService.logIn(request, username, password);
        } catch (Exception ex) {
            error = "Wrong username or password";
            return "redirect:redirect";
        }
        return "redirect:/products/shop";
    }

    @PostMapping("/changeUserDetails")
    public String changeUserDetails(HttpServletRequest request, @RequestParam String newUsername, @RequestParam String newMail, @RequestParam String password) {
        try {
            authenticationService.changeUserDetails(request, newUsername, password, newMail);
        } catch (Exception e) {
            error = e.getMessage();
            return "redirect:redirect";
        }
        error = "Changes saved successfully";
        return "redirect:redirect";
    }

    @PostMapping("/changeUserPassword")
    public String changeUserPassword(HttpServletRequest request, @RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            authenticationService.changeUserPassword(request, oldPassword, newPassword);
        } catch (Exception e) {
            error = e.getMessage();
            return "redirect:redirect";
        }
        error = "Changes saved successfully";
        return "redirect:redirect";
    }
}
