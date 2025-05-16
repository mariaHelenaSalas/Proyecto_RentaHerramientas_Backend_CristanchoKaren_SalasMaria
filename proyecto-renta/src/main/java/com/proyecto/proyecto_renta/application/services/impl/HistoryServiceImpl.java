package com.proyecto.proyecto_renta.application.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IHistoryService;
import com.proyecto.proyecto_renta.domain.entities.History;
import com.proyecto.proyecto_renta.infrastructure.repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements IHistoryService {

    private final HistoryRepository repository;

    public HistoryServiceImpl(HistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<History> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<History> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public History save(History history) {
        return repository.save(history);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
