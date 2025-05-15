package com.proyecto.proyecto_renta.application.usecase;


import com.proyecto.proyecto_renta.domain.entities.Reservation;
import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.infrastructure.repository.ReservationRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.ToolRepository;

import org.springframework.stereotype.Service;

@Service
public class CreateReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final ToolRepository toolRepository;

    public CreateReservationUseCase(ReservationRepository reservationRepository, ToolRepository toolRepository) {
        this.reservationRepository = reservationRepository;
        this.toolRepository = toolRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        Tool tool = toolRepository.findById(reservation.getTool().getIdTool())
                .orElseThrow(() -> new IllegalArgumentException("Herramienta no encontrada."));

        // Validar disponibilidad
        if (!tool.getStatus().equals(Tool.Status.AVAILABLE)) {
            throw new IllegalStateException("La herramienta no está disponible.");
        }

        // Cambiar estado a "Alquilado"
        tool.setStatus(Tool.Status.RENTED);
        toolRepository.save(tool);

        // Guardar reserva
        return reservationRepository.save(reservation);
    }
}