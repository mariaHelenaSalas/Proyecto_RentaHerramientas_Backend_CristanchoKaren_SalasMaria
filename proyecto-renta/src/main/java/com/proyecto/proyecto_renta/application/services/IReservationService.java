package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Reservation;

public interface IReservationService {
    
    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation save(Reservation reservation);

    void deleteById(Long id);

}
