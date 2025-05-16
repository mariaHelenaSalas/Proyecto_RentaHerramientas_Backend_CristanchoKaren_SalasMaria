package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 90)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 220)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    public enum Role {
        ADMIN, PROVIDER, CLIENT
    }
}
