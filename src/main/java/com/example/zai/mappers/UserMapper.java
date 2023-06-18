package com.example.zai.mappers;

import com.example.zai.dto.UserDTO;
import com.example.zai.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return UserDTO.builder().role(user.getRole()).username(user.getUsername()).email(user.getEmail()).id(user.getId()).build();
    }

}
