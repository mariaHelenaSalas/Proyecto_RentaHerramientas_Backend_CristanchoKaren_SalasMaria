package com.proyecto.proyecto_renta.infrastructure.repository;


import com.proyecto.proyecto_renta.domain.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
