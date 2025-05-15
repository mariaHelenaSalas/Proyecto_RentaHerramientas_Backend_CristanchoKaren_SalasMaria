package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.User;

public interface IUserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteBydyId(Long id);
}
