package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Returns")
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReturn;

    @OneToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime returnDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EquipmentStatus equipmentStatus;

    private String comments;

    public enum EquipmentStatus {
        GOOD, DAMAGED, LOST
    }
}
