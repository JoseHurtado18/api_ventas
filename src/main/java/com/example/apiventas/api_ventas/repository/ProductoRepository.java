package com.example.apiventas.api_ventas.repository;

import com.example.apiventas.api_ventas.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByStockLessThanEqual(int stockMinimo);

    Optional<Producto> findByCodigo(String codigo);


}