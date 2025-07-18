package com.example.apiventas.api_ventas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.apiventas.api_ventas.dto.ProductoDTO;
import com.example.apiventas.api_ventas.models.Producto;
import com.example.apiventas.api_ventas.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Tag(name = "Inventario", description = "Operaciones relacionadas con productos del inventario")
public class InventarioRestController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(summary = "Listar todos los productos del inventario")
    @GetMapping("/inventario")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = inventarioService.listarProductos().stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Obtener un producto por su ID")
    @GetMapping("/inventario/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable("id") Integer id) {
        return inventarioService.buscarProductoPorId(id)
            .map(producto -> ResponseEntity.ok(new ProductoDTO(producto)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar productos por categoría")
    @GetMapping("/inventario/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> buscarPorCategoria(@PathVariable("categoria") String categoria) {
        List<ProductoDTO> productos = inventarioService.buscarPorCategoria(categoria).stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Buscar productos por nombre")
    @GetMapping("/inventario/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@RequestParam("buscar") String nombre) {
        List<ProductoDTO> productos = inventarioService.buscarPorNombre(nombre).stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping("admin/inventario")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos para registrar un nuevo producto de tipo sierra en el inventario",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n" +
                        "  \"codigo\": \"CU-002-001\",\n" +
                        "  \"nombre\": \"Sierra Hidráulica de Alta Precisión\",\n" +
                        "  \"descripcion\": \"Sierra hidráulica ideal para cortes industriales en acero\",\n" +
                        "  \"precio\": 850000,\n" +
                        "  \"stock\": 12,\n" +
                        "  \"categoria\": \"Sierras Hidráulicas\",\n" +
                        "  \"stockMinimo\": 3\n" +
                        "}"
            )
        )
    )
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO dto) {
        Producto nuevo = inventarioService.guardarProducto(new Producto(dto));
        return ResponseEntity.ok(new ProductoDTO(nuevo));
    }

    @Operation(summary = "Actualizar un producto existente")
    @PutMapping("/inventario/{id}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos para actualizar un producto existente en el inventario",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n" +
                        "  \"codigo\": \"CU-002-001\",\n" +
                        "  \"nombre\": \"Sierra Hidráulica de Corte Rápido\",\n" +
                        "  \"descripcion\": \"Sierra hidráulica optimizada para cortes industriales de alta velocidad\",\n" +
                        "  \"precio\": 950000,\n" +
                        "  \"stock\": 18,\n" +
                        "  \"categoria\": \"Sierras Hidráulicas\",\n" +
                        "  \"stockMinimo\": 5\n" +
                        "}"
            )
        )
    )
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Integer id, @RequestBody ProductoDTO dto) {
        Producto actualizado = inventarioService.actualizarProducto(id, new Producto(dto));
        return ResponseEntity.ok(new ProductoDTO(actualizado));
    }

    @Operation(summary = "Eliminar un producto por su ID")
    @DeleteMapping("/inventario/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable("id") Integer id) {
        inventarioService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Gestionar nuevo stock")
    @PutMapping("/inventario/recepcionStock")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Lista de productos con código y stock para actualizar",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "[\n" +
                        "  {\n" +
                        "    \"codigo\": \"CU-001-001\",\n" +
                        "    \"stock\": 25\n" +
                        "  }" +
                        "]"
            )
        )
    )
    public ResponseEntity<String> newStock(@RequestBody List<Producto> productos) {
        inventarioService.nuevoStock(productos);
        return ResponseEntity.ok("nuevo stock agregado correctamente");
    }
}
