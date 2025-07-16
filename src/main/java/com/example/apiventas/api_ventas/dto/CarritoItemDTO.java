package com.example.apiventas.api_ventas.dto;

import lombok.Data;

@Data
public class CarritoItemDTO {
    private String codigoProducto;
    private int cantidad;
}