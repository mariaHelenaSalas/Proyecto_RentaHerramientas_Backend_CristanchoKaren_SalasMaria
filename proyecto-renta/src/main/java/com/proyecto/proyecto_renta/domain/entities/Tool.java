package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Tools")
public class Tool {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTool;

    @ManyToOne
    @JoinColumn(name = "id_provider", nullable = false)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "rental_cost", nullable = false)
    private BigDecimal rentalCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.AVAILABLE;

    @Column(name = "image_url")
    private String imageUrl;

    public enum Status {
        AVAILABLE, RENTED, UNDER_MAINTENANCE
    }

}
