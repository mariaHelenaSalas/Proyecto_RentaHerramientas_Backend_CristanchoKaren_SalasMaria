package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Payment;

public interface IPaymentService {
    List <Payment> findAll();
    Payment findById(Long id);
    Payment save(Payment payment);
    void deleteById(Long id);
}
