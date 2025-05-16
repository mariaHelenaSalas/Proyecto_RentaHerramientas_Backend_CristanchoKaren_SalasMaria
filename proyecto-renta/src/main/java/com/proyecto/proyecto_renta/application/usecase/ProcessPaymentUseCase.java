package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Payment;
import com.proyecto.proyecto_renta.infrastructure.repository.PaymentRepository;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class ProcessPaymentUseCase {
    private final PaymentRepository paymentRepository;

    public ProcessPaymentUseCase(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Payment payment) {
        // Validar que el monto sea positivo
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }

        // Marcar pago como completado
        payment.setStatus(Payment.Status.COMPLETED);
        return paymentRepository.save(payment);
    }
}