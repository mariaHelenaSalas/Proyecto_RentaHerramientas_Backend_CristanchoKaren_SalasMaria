package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Payment;

public interface IPaymentService {
    List<Payment> findAll();
    Optional<Payment> findById(Long id);
    Payment save(Payment payment);
    void deleteById(Long id);
    List<Payment> findByClientUserId(Long userId);
    List<Payment> findByProviderUserId(Long userId);
}
