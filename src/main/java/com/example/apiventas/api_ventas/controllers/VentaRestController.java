package com.example.apiventas.api_ventas.controllers;

import com.example.apiventas.api_ventas.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.apiventas.api_ventas.dto.*;
import com.example.apiventas.api_ventas.models.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Tag(name = "Ventas", description = "Operaciones relacionadas con el procesamiento de ventas")
public class VentaRestController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Crear una nueva venta")
    @PostMapping("/cliente/venta")
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody List<CarritoItemDTO> items) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String clienteId = authentication.getName();

        Venta venta = ventaService.procesarVenta(clienteId, items);
        return ResponseEntity.ok(convertirAVentaDTO(venta));
    }

    @Operation(summary = "Listar ventas por cliente")
    @GetMapping("/cliente/venta/{cliente}")
    public ResponseEntity<List<VentaDTO>> listarVentasPorCliente(@PathVariable("cliente") String cliente) {
        List<VentaDTO> ventas = ventaService.listarVentasPorCliente(cliente).stream()
            .map(this::convertirAVentaDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ventas);
    }

    @Operation(summary = "Obtener una venta por su ID")
    @GetMapping("/admin/venta/{id}")
    public ResponseEntity<VentaDTO> obtenerVenta(@PathVariable("id") Integer id) {
        return ventaService.obtenerVenta(id)
            .map(venta -> ResponseEntity.ok(convertirAVentaDTO(venta)))
            .orElse(ResponseEntity.notFound().build());
    }

    // método reutilizable
    private VentaDTO convertirAVentaDTO(Venta venta) {
        List<DetalleVentaDTO> detallesDTO = venta.getDetalles().stream()
            .map(detalle -> {
                DetalleVentaDTO dto = new DetalleVentaDTO();
                dto.setProductoId(detalle.getProducto().getId());
                dto.setProductoNombre(detalle.getProducto().getNombre());
                dto.setCantidad(detalle.getCantidad());
                dto.setPrecioUnitario(detalle.getPrecioUnitario());
                dto.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
                return dto;
            })
            .collect(Collectors.toList());

        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setCliente(venta.getCliente());
        dto.setFecha(venta.getFecha());
        dto.setEstado(venta.getEstado());
        dto.setTotal(venta.getTotal());
        dto.setDetalles(detallesDTO);

        return dto;
    }
}
