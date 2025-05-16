package com.proyecto.proyecto_renta.infrastructure.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IReservationService;
import com.proyecto.proyecto_renta.domain.entities.Reservation;
import com.proyecto.proyecto_renta.infrastructure.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository repository;

    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Reservation> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
