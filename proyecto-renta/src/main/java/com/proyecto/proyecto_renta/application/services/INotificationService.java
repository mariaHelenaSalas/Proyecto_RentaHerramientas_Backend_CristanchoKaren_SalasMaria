package com.proyecto.proyecto_renta.application.services;

import java.util.List;

public interface INotificationService {
    List<String> findAll();

    String findById(Long id);

    String save(String notification);
    
    void deleteById(Long id);


}
