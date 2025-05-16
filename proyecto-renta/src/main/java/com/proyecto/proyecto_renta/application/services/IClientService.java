package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Client;

public interface IClientService {
    List<Client> findAll();
    Optional<Client> findById(Long id);
    Client save(Client client);
    void deleteById(Long id);
}
