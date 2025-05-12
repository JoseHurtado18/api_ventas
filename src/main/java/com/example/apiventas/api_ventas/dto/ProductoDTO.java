package com.example.apiventas.api_ventas.dto;

import com.example.apiventas.api_ventas.models.Producto;
import lombok.Data;

@Data
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String categoria;

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.stock = producto.getStock();
        this.categoria = producto.getCategoria();
    }
}