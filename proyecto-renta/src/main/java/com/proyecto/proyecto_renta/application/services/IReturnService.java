package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Return;

public interface IReturnService {

    List<Return> findAll();

    Return findById(Long id);

    Return save(Return returnEntity);

    void deleteById(Long id);

}
