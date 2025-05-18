package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Return;
import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.domain.entities.Reservation;
import com.proyecto.proyecto_renta.domain.exceptions.BadRequestException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto_renta.infrastructure.repository.ReservationRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.ReturnRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.ToolRepository;
import com.proyecto.proyecto_renta.infrastructure.websocket.NotificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegisterReturnUseCase {
    private final ReturnRepository returnRepository;
    private final ToolRepository toolRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public RegisterReturnUseCase(ReturnRepository returnRepository, 
                               ToolRepository toolRepository, 
                               ReservationRepository reservationRepository,
                               NotificationService notificationService) {
        this.returnRepository = returnRepository;
        this.toolRepository = toolRepository;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Return registerReturn(Return returnRecord) {
        // Validate reservation exists
        Reservation reservation = reservationRepository.findById(returnRecord.getReservation().getIdReservation())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", returnRecord.getReservation().getIdReservation()));
        
        // Validate reservation status
        if (reservation.getStatus() != Reservation.Status.CONFIRMED) {
            throw new BadRequestException("Cannot register return for a reservation that is not confirmed");
        }
        
        // Get the tool
        Tool tool = toolRepository.findById(reservation.getTool().getIdTool())
                .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", reservation.getTool().getIdTool()));
        
        // Set return details
        returnRecord.setReservation(reservation);
        returnRecord.setReturnDate(LocalDateTime.now());
        
        // Update tool status based on equipment status
        if (returnRecord.getEquipmentStatus() == Return.EquipmentStatus.GOOD) {
            tool.setStatus(Tool.Status.AVAILABLE);
        } else if (returnRecord.getEquipmentStatus() == Return.EquipmentStatus.DAMAGED) {
            tool.setStatus(Tool.Status.UNDER_MAINTENANCE);
        }
        
        // Update reservation status
        reservation.setStatus(Reservation.Status.COMPLETED);
        
        // Save changes
        toolRepository.save(tool);
        reservationRepository.save(reservation);
        
        // Save the return record
        Return savedReturn = returnRepository.save(returnRecord);
        
        // Send notifications
        notificationService.sendReturnNotification(
            tool.getProvider().getUser().getIdUser(),
            reservation.getClient().getUser().getIdUser(),
            tool.getName()
        );
        
        return savedReturn;
    }
}
