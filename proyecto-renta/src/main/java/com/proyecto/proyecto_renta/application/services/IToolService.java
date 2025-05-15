package com.proyecto.proyecto_renta.application.services;

import java.util.List;

public interface IToolService {

    List<String> findAll();

    String findById(Long id);

    String save(String tool); 
      
    void deleteById(Long id);


}
