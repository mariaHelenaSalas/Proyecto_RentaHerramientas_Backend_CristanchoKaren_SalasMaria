package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Invoice;
import com.proyecto.proyecto_renta.domain.entities.Payment;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto_renta.infrastructure.repository.InvoiceRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GenerateInvoiceUseCase {
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public GenerateInvoiceUseCase(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Invoice generateInvoice(Long paymentId) {
        // Find the payment
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));
        
        // Check if invoice already exists
        if (invoiceRepository.findByPaymentIdPayment(payment.getIdPayment()).isPresent()) {
            throw new IllegalStateException("Invoice already exists for this payment");
        }
        
        // Generate invoice number
        String invoiceNumber = generateInvoiceNumber();
        
        // Create invoice
        Invoice invoice = new Invoice();
        invoice.setPayment(payment);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setGenerationDate(LocalDateTime.now());
        
        // Generate PDF URL (in a real system, this would create an actual PDF)
        String pdfUrl = "https://example.com/invoices/" + invoiceNumber + ".pdf";
        invoice.setPdfUrl(pdfUrl);
        
        // Save and return the invoice
        return invoiceRepository.save(invoice);
    }
    
    private String generateInvoiceNumber() {
        // Generate a unique invoice number based on timestamp
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "INV-" + now.format(formatter);
    }
}
