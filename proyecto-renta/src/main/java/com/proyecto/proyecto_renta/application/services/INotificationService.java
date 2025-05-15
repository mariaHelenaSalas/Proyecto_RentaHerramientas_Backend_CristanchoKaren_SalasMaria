package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Notification;

public interface INotificationService {
    List<Notification> findAll();
    Notification findById(Long id);
    Notification save(Notification notification);
    void deleteById(Long id);
}
