package com.proyecto.proyecto_renta.domain.dto;

import com.proyecto.proyecto_renta.domain.entities.User;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private User.Role role;
    private LocalDateTime registrationDate;
    
    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getIdUser());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setRegistrationDate(user.getRegistrationDate());
        return response;
    }
}
