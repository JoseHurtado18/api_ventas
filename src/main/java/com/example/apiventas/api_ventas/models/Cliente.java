package com.example.apiventas.api_ventas.models;

import jakarta.persistence.*;
import lombok.Data;
import com.example.apiventas.api_ventas.dto.ClienteDTO;

@Data
@Entity
@Table(name = "CLIENTE")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String email;
    private String password;

    public Cliente() {
        
    }

    public Cliente(ClienteDTO dto) {
        this.id = dto.getId();
        this.nombre = dto.getNombre();
        this.email = dto.getEmail();
    }
}