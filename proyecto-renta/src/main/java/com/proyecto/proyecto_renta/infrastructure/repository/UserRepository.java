package com.proyecto.proyecto_renta.infrastructure.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto_renta.domain.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
   
    Optional<User> findByEmail(String email);

}
