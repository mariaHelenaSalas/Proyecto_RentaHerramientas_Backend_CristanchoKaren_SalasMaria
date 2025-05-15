package com.proyecto.proyecto_renta.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.proyecto_renta.domain.entities.Category;

public interface CategoryRepository  extends CrudRepository<Category, Long> {
  
    

}
