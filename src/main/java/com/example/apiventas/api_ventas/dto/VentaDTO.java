package com.example.apiventas.api_ventas.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaDTO {
    private Integer id;
    private String cliente;
    private LocalDateTime fecha;
    private String estado;
    private double total;
    private List<DetalleVentaDTO> detalles;
}