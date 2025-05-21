package com.proyecto.proyecto_renta.application.services;
import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Category;


public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    boolean existsById(Long id);
}
