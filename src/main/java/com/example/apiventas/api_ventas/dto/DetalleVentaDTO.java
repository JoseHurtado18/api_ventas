package com.example.apiventas.api_ventas.dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {
    private Integer productoId;
    private String codigoProducto;
    private String productoNombre;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
}