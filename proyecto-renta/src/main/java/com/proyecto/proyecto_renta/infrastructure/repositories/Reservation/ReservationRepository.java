package com.proyecto.proyecto_renta.infrastructure.repositories.Reservation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto_renta.domain.entities.Reservation;
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long clientId);
}
