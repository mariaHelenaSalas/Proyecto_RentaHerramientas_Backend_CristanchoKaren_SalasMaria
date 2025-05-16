package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Payment;
import com.proyecto.proyecto_renta.infrastructure.repository.PaymentRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GenerateProfitReportUseCase {
    private final PaymentRepository paymentRepository;

    public GenerateProfitReportUseCase(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public BigDecimal generateProfitReport() {
        List<Payment> payments = (List<Payment>) paymentRepository.findAll();
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
