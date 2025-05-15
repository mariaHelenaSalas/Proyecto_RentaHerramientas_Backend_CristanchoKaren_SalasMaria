package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistory;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_tool", nullable = false)
    private Tool tool;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "id_payment")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "id_return")
    private Return returnRecord;

    @Enumerated(EnumType.STRING)
    private Event event;

    @Column(nullable = false, updatable = false)
    private LocalDateTime eventDate = LocalDateTime.now();

    public enum Event {
        RESERVATION_CREATED, RESERVATION_CONFIRMED, RESERVATION_CANCELLED,
        PAYMENT_MADE, RETURN_RECEIVED, EQUIPMENT_DAMAGE_REPORTED, REMINDER_SENT
    }
}