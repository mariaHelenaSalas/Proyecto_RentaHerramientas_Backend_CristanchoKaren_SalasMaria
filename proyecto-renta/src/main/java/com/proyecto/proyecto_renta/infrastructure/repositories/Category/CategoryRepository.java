package com.proyecto.proyecto_renta.infrastructure.repositories.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto_renta.domain.entities.Category;
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
