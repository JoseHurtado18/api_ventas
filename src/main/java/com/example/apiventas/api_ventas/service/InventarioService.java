package com.example.apiventas.api_ventas.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiventas.api_ventas.models.Producto;
import com.example.apiventas.api_ventas.repository.ProductoRepository;

@Service
public class InventarioService {

    @Autowired
    private ProductoRepository repository;

    public List<Producto> ListaProductos(){
        return repository.findAll();
    }

    public Optional<Producto> BuscarId(Integer id){
        return repository.findById(id);
    }

    public Producto update(Integer id, Producto p){
        Producto productoExiste = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no existe" +id));
        return repository.save(productoExiste);
    }

    public Producto guardar(Producto producto){
        return (Producto) repository.save(producto);
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}
