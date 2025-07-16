package com.example.apiventas.api_ventas.controllers;

import com.example.apiventas.api_ventas.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Lista de productos a vender, incluyendo el código y la cantidad de cada uno",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "[\n" +
                        "  {\n" +
                        "    \"codigoProducto\": \"CU-002-001\",\n" +
                        "    \"cantidad\": 2\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"codigoProducto\": \"CU-001-002\",\n" +
                        "    \"cantidad\": 1\n" +
                        "  }\n" +
                        "]"
            )
        )
    )

    public ResponseEntity<VentaDTO> crearVenta(@RequestBody List<CarritoItemDTO> items) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String clienteId = authentication.getName();

        Venta venta = ventaService.procesarVenta(clienteId, items);
        return ResponseEntity.ok(convertirAVentaDTO(venta));
    }

    @Operation(summary = "Listar ventas del cliente")
    @GetMapping("/cliente/venta")
    public ResponseEntity<List<VentaDTO>> listarVentasPorCliente() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String clienteId = authentication.getName();

        List<VentaDTO> ventas = ventaService.listarVentasPorCliente(clienteId).stream()
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
                dto.setCodigoProducto(detalle.getProducto().getCodigo());
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
