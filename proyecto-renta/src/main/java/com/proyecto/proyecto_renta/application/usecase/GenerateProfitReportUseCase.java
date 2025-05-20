package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.dto.ToolUsageReport;
import com.proyecto.proyecto_renta.domain.entities.Payment;
import com.proyecto.proyecto_renta.infrastructure.repository.PaymentRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.ReservationRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenerateProfitReportUseCase {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public GenerateProfitReportUseCase(PaymentRepository paymentRepository, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    public BigDecimal generateProfitReport(LocalDate startDate, LocalDate endDate) {
        List<Payment> payments;
        
        if (startDate != null && endDate != null) {
            payments = paymentRepository.findByPaymentDateBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        } else {
            payments = paymentRepository.findAll();
        }
        
        return payments.stream()
                .filter(payment -> payment.getStatus() == Payment.Status.COMPLETED)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal generateProviderProfitReport(Long providerId, LocalDate startDate, LocalDate endDate) {
        List<Payment> payments;
        
        if (startDate != null && endDate != null) {
            payments = paymentRepository.findByProviderUserIdAndPaymentDateBetween(
                providerId, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        } else {
            payments = paymentRepository.findByProviderUserId(providerId);
        }
        
        return payments.stream()
                .filter(payment -> payment.getStatus() == Payment.Status.COMPLETED)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<ToolUsageReport> generateToolUsageReport(LocalDate startDate, LocalDate endDate) {
        List<Payment> payments;
        
        if (startDate != null && endDate != null) {
            payments = paymentRepository.findByPaymentDateBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        } else {
            payments = paymentRepository.findAll();
        }
        
        // Agrupar pagos por herramienta
        Map<Long, List<Payment>> paymentsByTool = payments.stream()
                .filter(payment -> payment.getStatus() == Payment.Status.COMPLETED)
                .collect(Collectors.groupingBy(p -> p.getReservation().getTool().getIdTool()));
        
        // Crear DTO de informes
        List<ToolUsageReport> report = new ArrayList<>();
        
        paymentsByTool.forEach((toolId, toolPayments) -> {
            if (!toolPayments.isEmpty()) {
                Payment firstPayment = toolPayments.get(0);
                
                ToolUsageReport dto = new ToolUsageReport();
                dto.setToolId(toolId);
                dto.setToolName(firstPayment.getReservation().getTool().getName());
                dto.setCategory(firstPayment.getReservation().getTool().getCategory().getName());
                dto.setProviderName(firstPayment.getReservation().getTool().getProvider().getUser().getName());
                dto.setReservationCount((long) toolPayments.size());
                
                BigDecimal totalRevenue = toolPayments.stream()
                        .map(Payment::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                dto.setTotalRevenue(totalRevenue);
                
                report.add(dto);
            }
        });
        
        // Ordenar por ingresos (los más altos primero)
        return report.stream()
                .sorted((a, b) -> b.getTotalRevenue().compareTo(a.getTotalRevenue()))
                .collect(Collectors.toList());
    }
    
    public List<ToolUsageReport> generateProviderToolUsageReport(Long providerId, LocalDate startDate, LocalDate endDate) {
        List<Payment> payments;
        
        if (startDate != null && endDate != null) {
            payments = paymentRepository.findByProviderUserIdAndPaymentDateBetween(
                providerId, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        } else {
            payments = paymentRepository.findByProviderUserId(providerId);
        }
        
        // Agrupar pagos por herramienta
        Map<Long, List<Payment>> paymentsByTool = payments.stream()
                .filter(payment -> payment.getStatus() == Payment.Status.COMPLETED)
                .collect(Collectors.groupingBy(p -> p.getReservation().getTool().getIdTool()));
        
        // Crear DTO de informes
        List<ToolUsageReport> report = new ArrayList<>();
        
        paymentsByTool.forEach((toolId, toolPayments) -> {
            if (!toolPayments.isEmpty()) {
                Payment firstPayment = toolPayments.get(0);
                
                ToolUsageReport dto = new ToolUsageReport();
                dto.setToolId(toolId);
                dto.setToolName(firstPayment.getReservation().getTool().getName());
                dto.setCategory(firstPayment.getReservation().getTool().getCategory().getName());
                dto.setProviderName(firstPayment.getReservation().getTool().getProvider().getUser().getName());
                dto.setReservationCount((long) toolPayments.size());
                
                BigDecimal totalRevenue = toolPayments.stream()
                        .map(Payment::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                dto.setTotalRevenue(totalRevenue);
                
                report.add(dto);
            }
        });
        
        // Ordenar por ingresos (los más altos primero)
        return report.stream()
                .sorted((a, b) -> b.getTotalRevenue().compareTo(a.getTotalRevenue()))
                .collect(Collectors.toList());
    }
}
