package com.proyecto.proyecto_renta.infrastructure.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IProviderService;
import com.proyecto.proyecto_renta.domain.entities.Provider;
import com.proyecto.proyecto_renta.infrastructure.repository.ProviderRepository;

@Service
public class ProviderServiceImpl implements IProviderService {

    private final ProviderRepository repository;

    public ProviderServiceImpl(ProviderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Provider> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Provider> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Provider save(Provider provider) {
        return repository.save(provider);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
