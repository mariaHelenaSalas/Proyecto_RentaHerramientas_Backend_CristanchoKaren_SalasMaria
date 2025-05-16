package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Reservation;

public interface IReservationService {
    List<Reservation> findAll();
    Optional<Reservation> findById(Long id);
    Reservation save(Reservation reservation);
    void deleteById(Long id);
}
