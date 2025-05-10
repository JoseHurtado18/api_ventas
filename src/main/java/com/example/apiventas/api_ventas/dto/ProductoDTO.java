package com.example.apiventas.api_ventas.dto;

import com.example.apiventas.api_ventas.models.Producto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoDTO {
    private Integer id;
    private String name;
    private int precio;
    private int cantidad;

    public ProductoDTO(Producto p_Producto) {
        this.id = p_Producto.getId();
        this.name = p_Producto.getName();
        this.precio = p_Producto.getPrecio();
        this.cantidad = p_Producto.getCantidad();
    }

    
}
