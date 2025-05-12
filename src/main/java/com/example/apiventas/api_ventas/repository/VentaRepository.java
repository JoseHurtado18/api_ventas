package com.example.apiventas.api_ventas.repository;

import com.example.apiventas.api_ventas.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByCliente(String cliente);
    List<Venta> findByEstado(String estado);
}