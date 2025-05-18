package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Payment;
import com.proyecto.proyecto_renta.domain.entities.Reservation;
import com.proyecto.proyecto_renta.domain.exceptions.BadRequestException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto_renta.infrastructure.repository.PaymentRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.ReservationRepository;
import com.proyecto.proyecto_renta.infrastructure.websocket.NotificationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public ProcessPaymentUseCase(PaymentRepository paymentRepository, 
                                ReservationRepository reservationRepository,
                                NotificationService notificationService) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Payment processPayment(Payment payment) {
        // Validate payment amount
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Payment amount must be greater than zero");
        }
        
        // Validate payment method
        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
            throw new BadRequestException("Payment method is required");
        }
        
        // Validate reservation exists
        Reservation reservation = reservationRepository.findById(payment.getReservation().getIdReservation())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", payment.getReservation().getIdReservation()));
        
        // Set payment details
        payment.setReservation(reservation);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(Payment.Status.COMPLETED);
        
        // Update reservation status
        reservation.setStatus(Reservation.Status.CONFIRMED);
        reservationRepository.save(reservation);
        
        // Save the payment
        Payment savedPayment = paymentRepository.save(payment);
        
        // Send notifications
        notificationService.sendPaymentNotification(
            reservation.getTool().getProvider().getUser().getIdUser(),
            reservation.getClient().getUser().getIdUser(),
            reservation.getTool().getName()
        );
        
        return savedPayment;
    }
}
