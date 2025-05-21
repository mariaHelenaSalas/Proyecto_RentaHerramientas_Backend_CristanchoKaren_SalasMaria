package com.proyecto.proyecto_renta.infrastructure.repository;

import com.proyecto.proyecto_renta.domain.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.reservation.client.user.idUser = :userId")
    List<Payment> findByClientUserId(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Payment p WHERE p.reservation.tool.provider.user.idUser = :userId")
    List<Payment> findByProviderUserId(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findByPaymentDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM Payment p WHERE p.reservation.tool.provider.user.idUser = :userId AND p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findByProviderUserIdAndPaymentDateBetween(
            @Param("userId") Long userId, 
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate);
}
