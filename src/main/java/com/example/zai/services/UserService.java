package com.example.zai.services;

import com.example.zai.dto.UserDTO;
import com.example.zai.enums.Roles;
import com.example.zai.mappers.UserMapper;
import com.example.zai.models.User;
import com.example.zai.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers()
    {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    public void changeUserRole(Long id, Roles role) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(role);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }







}
