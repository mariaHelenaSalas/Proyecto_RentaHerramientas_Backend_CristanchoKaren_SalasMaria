package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvoice;

    @OneToOne
    @JoinColumn(name = "id_payment", nullable = false)
    private Payment payment;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime generationDate = LocalDateTime.now();

    private String pdfUrl;
}
