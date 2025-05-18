package com.proyecto.proyecto_renta.infrastructure.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Override
    public List<Notification> findByUserId(Long userId) {
        return repository.findByUserIdUser(userId);
    }
    
    @Override
    public List<Notification> findUnreadByUserId(Long userId) {
        return repository.findByUserIdUserAndReadFalse(userId);
    }
    
    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        repository.markAllAsReadByUserId(userId);
    }
}
