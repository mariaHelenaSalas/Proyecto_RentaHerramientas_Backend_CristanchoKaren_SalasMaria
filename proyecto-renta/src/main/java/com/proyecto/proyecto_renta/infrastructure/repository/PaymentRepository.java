package com.proyecto.proyecto_renta.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.proyecto_renta.domain.entities.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    List<Payment> findByStatus(Payment.Status status);
}
