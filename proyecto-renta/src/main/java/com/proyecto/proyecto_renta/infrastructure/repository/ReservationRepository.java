package com.proyecto.proyecto_renta.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.proyecto_renta.domain.entities.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long clientId);
}
