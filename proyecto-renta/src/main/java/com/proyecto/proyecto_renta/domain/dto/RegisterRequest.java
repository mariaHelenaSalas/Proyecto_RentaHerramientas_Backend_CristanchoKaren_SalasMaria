package com.proyecto.proyecto_renta.domain.dto;

import com.proyecto.proyecto_renta.domain.entities.User;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private User.Role role;
    private String phone;
    private String address;
}
