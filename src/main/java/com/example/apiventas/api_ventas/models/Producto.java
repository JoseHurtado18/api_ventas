package com.example.apiventas.api_ventas.models;

import com.example.apiventas.api_ventas.dto.ProductoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PRODUCTO")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int stockMinimo;
    private String categoria;

    public Producto() {
    }

    public Producto(ProductoDTO dto) {
        this.id = dto.getId();
        this.nombre = dto.getNombre();
        this.descripcion = dto.getDescripcion();
        this.categoria = dto.getCategoria();
        this.precio = dto.getPrecio();
        this.stock = dto.getStock();
        this.stockMinimo = dto.getStockMinimo();
    
    }
}