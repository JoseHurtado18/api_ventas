package com.example.apiventas.api_ventas.service;


import com.example.apiventas.api_ventas.models.Producto;
import com.example.apiventas.api_ventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventarioService {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private RequesicionesApiService requesicionesApiService;
    
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
    
    public Optional<Producto> buscarProductoPorId(Integer id) {
        return productoRepository.findById(id);
    }

    public Optional<Producto> buscarProductoPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
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

        //disparar alerta 
        if (producto.getStock() <= 10){
            requesicionesApiService.postAlerta(producto.getNombre(), "100");
        }

        return productoRepository.save(producto);
    }

    public void nuevoStock(List<Producto> productos) {
        for (Producto producto : productos) {
            productoRepository.findByCodigo(producto.getCodigo())
                .ifPresent(existente -> {
                    existente.setStock(existente.getStock() + producto.getStock());
                    productoRepository.save(existente);
                });
        }
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Integer id, Producto productoActualizado) {
        Producto productoExistente = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setStock(productoActualizado.getStock());

        return productoRepository.save(productoExistente);
    }

    public void eliminarProducto(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    public List<Producto> obtenerProductosConBajoStock() {
    return productoRepository.findAll().stream()
            .filter(p -> p.getStock() <= p.getStockMinimo())
            .collect(Collectors.toList());
    }

}