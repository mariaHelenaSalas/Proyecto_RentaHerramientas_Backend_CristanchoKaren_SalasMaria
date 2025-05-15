package com.proyecto.proyecto_renta.infrastructure.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto_renta.domain.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
   


}
