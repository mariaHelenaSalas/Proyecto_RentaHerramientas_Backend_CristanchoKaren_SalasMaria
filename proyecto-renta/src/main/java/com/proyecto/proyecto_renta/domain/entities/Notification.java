package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    private String message;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sentDate = LocalDateTime.now();

    private Boolean read = false;

    public enum Type {
        RESERVATION, PAYMENT, REMINDER
    }
}