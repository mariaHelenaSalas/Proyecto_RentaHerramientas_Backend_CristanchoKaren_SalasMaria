package com.proyecto.proyecto_renta.infrastructure.repositories.User;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.UserService;
import com.proyecto.proyecto_renta.domain.entities.User;

import jakarta.transaction.Transactional;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
