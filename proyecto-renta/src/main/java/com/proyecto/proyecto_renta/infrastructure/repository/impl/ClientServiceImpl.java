package com.proyecto.proyecto_renta.infrastructure.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IClientService;
import com.proyecto.proyecto_renta.domain.entities.Client;
import com.proyecto.proyecto_renta.infrastructure.repository.ClientRepository;

@Service
public class ClientServiceImpl implements IClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Client save(Client client) {
        return repository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

