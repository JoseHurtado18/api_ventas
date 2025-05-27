package com.example.apiventas.api_ventas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.apiventas.api_ventas.dto.ProductoDTO;
import com.example.apiventas.api_ventas.models.Producto;
import com.example.apiventas.api_ventas.service.AlertaService;
import com.example.apiventas.api_ventas.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Operaciones relacionadas con productos del inventario")
public class InventarioRestController {
    
    @Autowired
    private InventarioService inventarioService;
    private AlertaService alertaService;

    
    @Operation(summary = "Listar todos los productos del inventario")
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = inventarioService.listarProductos().stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }
    
    @Operation(summary = "Obtener un producto por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable("id") Integer id) {
        return inventarioService.buscarProductoPorId(id)
            .map(producto -> ResponseEntity.ok(new ProductoDTO(producto)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Buscar productos por categoría")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> buscarPorCategoria(@PathVariable("categoria") String categoria) {
        List<ProductoDTO> productos = inventarioService.buscarPorCategoria(categoria).stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }
    
    @Operation(summary = "Buscar productos por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@RequestParam("buscar") String nombre) {
        List<ProductoDTO> productos = inventarioService.buscarPorNombre(nombre).stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO dto) {
        Producto nuevo = inventarioService.guardarProducto(new Producto(dto));
        return ResponseEntity.ok(new ProductoDTO(nuevo));
    }

    @Operation(summary = "Actualizar un producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Integer id, @RequestBody ProductoDTO dto) {
        Producto actualizado = inventarioService.actualizarProducto(id, new Producto(dto));
        return ResponseEntity.ok(new ProductoDTO(actualizado));
    }

    @Operation(summary = "Eliminar un producto por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        inventarioService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar productos con stock bajo")
    @GetMapping("/alertas/stock-bajo")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosConStockBajo() {
        List<ProductoDTO> productos = alertaService.obtenerProductosConBajoStock()
                .stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

}