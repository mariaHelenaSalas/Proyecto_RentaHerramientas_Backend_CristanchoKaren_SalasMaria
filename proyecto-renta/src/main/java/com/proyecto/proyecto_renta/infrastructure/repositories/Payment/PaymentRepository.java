package com.proyecto.proyecto_renta.infrastructure.repositories.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto_renta.domain.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
