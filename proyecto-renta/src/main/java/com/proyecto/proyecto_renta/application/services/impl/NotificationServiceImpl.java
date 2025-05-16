package com.proyecto.proyecto_renta.application.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.INotificationService;
import com.proyecto.proyecto_renta.domain.entities.Notification;
import com.proyecto.proyecto_renta.infrastructure.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements INotificationService {

    private final NotificationRepository repository;

    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Notification> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
