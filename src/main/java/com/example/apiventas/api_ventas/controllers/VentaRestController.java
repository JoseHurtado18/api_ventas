package com.example.apiventas.api_ventas.controllers;

import com.example.apiventas.api_ventas.service.VentaService;
import com.example.apiventas.api_ventas.dto.*;
import com.example.apiventas.api_ventas.models.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ventas")
public class VentaRestController {
    
    @Autowired
    private VentaService ventaService;
    
    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(
            @RequestParam("cliente") String cliente,
            @RequestBody List<CarritoItemDTO> items) {
        
        Venta venta = ventaService.procesarVenta(cliente, items);
        return ResponseEntity.ok(convertirAVentaDTO(venta));
    }
    
    @GetMapping("/cliente/{cliente}")
    public ResponseEntity<List<VentaDTO>> listarVentasPorCliente(@PathVariable("cliente") String cliente) {
        List<VentaDTO> ventas = ventaService.listarVentasPorCliente(cliente).stream()
            .map(this::convertirAVentaDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ventas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerVenta(@PathVariable("id") Integer id) {
        return ventaService.obtenerVenta(id)
            .map(venta -> ResponseEntity.ok(convertirAVentaDTO(venta)))
            .orElse(ResponseEntity.notFound().build());
    }
    
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