package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Category;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
}
