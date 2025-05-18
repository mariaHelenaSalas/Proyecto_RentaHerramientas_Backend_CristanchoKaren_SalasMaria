package com.proyecto.proyecto_renta.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ToolUsageReportDTO {
    private Long toolId;
    private String toolName;
    private String category;
    private String providerName;
    private Long reservationCount;
    private BigDecimal totalRevenue;
}
