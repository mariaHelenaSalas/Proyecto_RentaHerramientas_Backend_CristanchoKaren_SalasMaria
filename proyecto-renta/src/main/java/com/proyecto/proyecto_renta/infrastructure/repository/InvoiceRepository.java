package com.proyecto.proyecto_renta.infrastructure.repository;

import com.proyecto.proyecto_renta.domain.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE i.payment.reservation.tool.provider.user.idUser = :userId")
    List<Invoice> findByProviderUserId(@Param("userId") Long userId);
    
    @Query("SELECT i FROM Invoice i WHERE i.payment.reservation.client.user.idUser = :userId")
    List<Invoice> findByClientUserId(@Param("userId") Long userId);
    
    Optional<Invoice> findByPaymentIdPayment(Long paymentId);
}
