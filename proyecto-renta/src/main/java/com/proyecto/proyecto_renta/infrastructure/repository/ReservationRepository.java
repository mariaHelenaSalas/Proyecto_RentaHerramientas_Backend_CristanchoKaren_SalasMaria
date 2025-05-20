package com.proyecto.proyecto_renta.infrastructure.repository;

import com.proyecto.proyecto_renta.domain.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientIdClient(Long clientId);
    List<Reservation> findByToolProviderUserIdUser(Long userId);
}
