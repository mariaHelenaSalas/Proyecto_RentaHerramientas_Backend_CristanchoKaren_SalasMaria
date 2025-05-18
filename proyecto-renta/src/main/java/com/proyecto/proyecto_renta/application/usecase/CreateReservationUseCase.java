package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Reservation;
import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.domain.exceptions.BadRequestException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto_renta.infrastructure.repository.ReservationRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.ToolRepository;
import com.proyecto.proyecto_renta.infrastructure.websocket.NotificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CreateReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final ToolRepository toolRepository;
    private final NotificationService notificationService;

    public CreateReservationUseCase(ReservationRepository reservationRepository, 
                                   ToolRepository toolRepository,
                                   NotificationService notificationService) {
        this.reservationRepository = reservationRepository;
        this.toolRepository = toolRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        // Validate reservation dates
        validateReservationDates(reservation);
        
        Tool tool = toolRepository.findById(reservation.getTool().getIdTool())
                .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", reservation.getTool().getIdTool()));

        // Validate tool availability
        if (tool.getStatus() != Tool.Status.AVAILABLE) {
            throw new BadRequestException("The tool is not available for reservation");
        }

        // Set initial status
        reservation.setStatus(Reservation.Status.PENDING);
        
        // Change tool status to RENTED
        tool.setStatus(Tool.Status.RENTED);
        toolRepository.save(tool);

        // Save the reservation
        Reservation savedReservation = reservationRepository.save(reservation);
        
        // Send notifications
        notificationService.sendReservationNotification(
            tool.getProvider().getUser().getIdUser(),
            reservation.getClient().getUser().getIdUser(),
            tool.getName()
        );
        
        return savedReservation;
    }
    
    private void validateReservationDates(Reservation reservation) {
        LocalDate now = LocalDate.now();
        
        // Start date must be in the future
        if (reservation.getStartDate() == null || reservation.getStartDate().isBefore(now)) {
            throw new BadRequestException("Start date must be in the future");
        }
        
        // End date must be after start date
        if (reservation.getEndDate() == null || reservation.getEndDate().isBefore(reservation.getStartDate())) {
            throw new BadRequestException("End date must be after start date");
        }
        
        // Maximum reservation period (e.g., 30 days)
        if (reservation.getEndDate().isAfter(reservation.getStartDate().plusDays(30))) {
            throw new BadRequestException("Maximum reservation period is 30 days");
        }
    }
}
