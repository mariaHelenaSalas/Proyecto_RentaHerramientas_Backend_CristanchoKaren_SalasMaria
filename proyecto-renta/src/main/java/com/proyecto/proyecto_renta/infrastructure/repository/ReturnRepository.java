package com.proyecto.proyecto_renta.infrastructure.repository;

import com.proyecto.proyecto_renta.domain.entities.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReturnRepository extends JpaRepository<Return, Long> {
    @Query("SELECT r FROM Return r WHERE r.reservation.tool.provider.user.idUser = :userId")
    List<Return> findByProviderUserId(@Param("userId") Long userId);
    
    @Query("SELECT r FROM Return r WHERE r.reservation.client.user.idUser = :userId")
    List<Return> findByClientUserId(@Param("userId") Long userId);
}
