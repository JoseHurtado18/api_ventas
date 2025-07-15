package com.example.apiventas.api_ventas.repository;

import com.example.apiventas.api_ventas.models.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByNombre(String nombre);
}