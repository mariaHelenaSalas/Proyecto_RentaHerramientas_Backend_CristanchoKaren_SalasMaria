package com.proyecto.proyecto_renta.application.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IReturnService;
import com.proyecto.proyecto_renta.domain.entities.Return;
import com.proyecto.proyecto_renta.infrastructure.repository.ReturnRepository;

@Service
public class ReturnServiceImpl implements IReturnService {

    private final ReturnRepository repository;

    public ReturnServiceImpl(ReturnRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Return> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Return> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Return save(Return entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}