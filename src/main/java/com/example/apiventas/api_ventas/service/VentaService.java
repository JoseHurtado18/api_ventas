package com.example.apiventas.api_ventas.service;

import com.example.apiventas.api_ventas.dto.CarritoItemDTO;
import com.example.apiventas.api_ventas.models.*;
import com.example.apiventas.api_ventas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {
    
    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    @Autowired
    private InventarioService inventarioService;
    
    @Transactional
    public Venta procesarVenta(String cliente, List<CarritoItemDTO> items) {
        // Crear la venta
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("PENDIENTE");
        venta.setTotal(0.0);
        
        Venta ventaGuardada = ventaRepository.save(venta);
        
        // Procesar detalles
        List<DetalleVenta> detalles = new ArrayList<>();
        double total = 0.0;
        
        for (CarritoItemDTO item : items) {
            Producto producto = inventarioService.buscarProductoPorId(item.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductoId()));
            
            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }
            
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(ventaGuardada);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            
            detalles.add(detalle);
            total += producto.getPrecio() * item.getCantidad();
            
            // Actualizar stock
            inventarioService.actualizarStock(producto.getId(), item.getCantidad());
        }
        
        // Guardar detalles y actualizar total
        detalleVentaRepository.saveAll(detalles);
        ventaGuardada.setDetalles(detalles);
        ventaGuardada.setTotal(total);
        ventaGuardada.setEstado("COMPLETADA");
        
        return ventaRepository.save(ventaGuardada);
    }
    
    public List<Venta> listarVentasPorCliente(String cliente) {
        return ventaRepository.findByCliente(cliente);
    }
    
    public Optional<Venta> obtenerVenta(Integer id) {
        return ventaRepository.findById(id);
    }
}
