package com.example.apiventas.api_ventas.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.apiventas.api_ventas.models.Producto;
import com.example.apiventas.api_ventas.repository.ProductoRepository;

@Service
public class AlertaService {

    private final ProductoRepository productoRepository;

    public AlertaService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerProductosConBajoStock() {
        return productoRepository.findByStockLessThanEqual(10);
    }
}
