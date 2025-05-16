package com.proyecto.proyecto_renta.application.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IPaymentService;
import com.proyecto.proyecto_renta.domain.entities.Payment;
import com.proyecto.proyecto_renta.infrastructure.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Payment save(Payment payment) {
        return repository.save(payment);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}