package com.example.apiventas.api_ventas.controllers;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiventas.api_ventas.models.Producto;
import com.example.apiventas.api_ventas.service.InventarioService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("api/inventario")
public class InventarioRestController {

    @Autowired
    private InventarioService service_inventario;

    @GetMapping
    public ResponseEntity<List<Producto>> getProductos(){
        List<Producto> productos = service_inventario.ListaProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Producto> add(@RequestBody Producto inProducto){
        Producto added = service_inventario.guardar(inProducto);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") Integer id, @RequestBody Producto producto){
        Optional<Producto> productoExiste = service_inventario.BuscarId(id);
        if(productoExiste.isPresent()){
            Producto actualizado = productoExiste.get();
            actualizado.setName(producto.getName());
            actualizado.setPrecio(producto.getPrecio());
            actualizado.setCantidad(producto.getCantidad());
            return new ResponseEntity<>(service_inventario.guardar(actualizado), HttpStatus.OK);
            
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable("id") Integer id){
        try{
            service_inventario.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }    
}
