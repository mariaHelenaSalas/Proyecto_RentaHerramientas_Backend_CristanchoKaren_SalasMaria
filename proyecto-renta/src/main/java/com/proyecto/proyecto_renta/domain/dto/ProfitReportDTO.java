package com.proyecto.proyecto_renta.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProfitReportDTO {
    private BigDecimal totalProfit;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate generatedAt;
}
