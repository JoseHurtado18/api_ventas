package com.example.apiventas.api_ventas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apiventas.api_ventas.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
