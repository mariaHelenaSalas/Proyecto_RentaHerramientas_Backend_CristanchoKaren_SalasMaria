package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Client;

public interface IClientService {
    List<Client> findAll();
    Client findById(Long id);
    Client save(Client client);
    void deleteById(Long id);
}
