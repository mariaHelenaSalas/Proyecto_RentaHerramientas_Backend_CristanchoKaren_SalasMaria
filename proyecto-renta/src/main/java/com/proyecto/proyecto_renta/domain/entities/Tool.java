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

    private String name;
    private String description;

    @Column(nullable = false)
    private BigDecimal rentalCost;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String imageUrl;

    public enum Status {
        AVAILABLE, RENTED, UNDER_MAINTENANCE, DISPONIBLE, ALQUILADO
    }

    public void setCostoAlquiler(double costoAlquiler) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCostoAlquiler'");
    }

    public void setEstado(Status disponible) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEstado'");
    }

    public Object getEstado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstado'");
    }
}