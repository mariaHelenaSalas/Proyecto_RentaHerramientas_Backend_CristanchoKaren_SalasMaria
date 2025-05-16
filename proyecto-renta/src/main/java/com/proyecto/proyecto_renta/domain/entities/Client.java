package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false, unique = true)
    private User user;

    @Column(length = 15)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;
}

