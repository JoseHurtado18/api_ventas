package com.example.apiventas.api_ventas.controllers;


import com.example.apiventas.api_ventas.dto.ProductoDTO;
import com.example.apiventas.api_ventas.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
public class InventarioRestController {
    
    @Autowired
    private InventarioService inventarioService;
    
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = inventarioService.listarProductos().stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable Integer id) {
        return inventarioService.buscarProductoPorId(id)
            .map(producto -> ResponseEntity.ok(new ProductoDTO(producto)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> buscarPorCategoria(@PathVariable String categoria) {
        List<ProductoDTO> productos = inventarioService.buscarPorCategoria(categoria).stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<ProductoDTO> productos = inventarioService.buscarPorNombre(nombre).stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }
}