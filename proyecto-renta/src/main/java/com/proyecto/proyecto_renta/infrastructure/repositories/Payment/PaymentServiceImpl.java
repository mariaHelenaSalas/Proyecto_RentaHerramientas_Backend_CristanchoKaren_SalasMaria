package com.proyecto.proyecto_renta.infrastructure.repositories.Payment;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.PaymentService;
import com.proyecto.proyecto_renta.domain.entities.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    public Payment processPayment(Payment paymentDTO) {
        if (paymentDTO.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return paymentRepository.save(paymentDTO);
    }
}
