package com.example.apiventas.api_ventas.service;


import com.example.apiventas.api_ventas.models.Producto;
import com.example.apiventas.api_ventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
    
    public Optional<Producto> buscarProductoPorId(Integer id) {
        return productoRepository.findById(id);
    }
    
    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public Producto actualizarStock(Integer id, int cantidad) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }
}