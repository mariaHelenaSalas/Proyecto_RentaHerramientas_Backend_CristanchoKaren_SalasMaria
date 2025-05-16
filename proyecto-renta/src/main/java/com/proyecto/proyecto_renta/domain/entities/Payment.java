package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPayment;

    @ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private BigDecimal amount;

    private String paymentMethod;

    @Column(nullable = false, updatable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, COMPLETED
    }
}