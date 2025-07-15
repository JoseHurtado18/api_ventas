package com.example.apiventas.api_ventas.models;

import jakarta.persistence.*;
import lombok.Data;
import com.example.apiventas.api_ventas.dto.AdminDTO;

@Data
@Entity
@Table(name = "ADMIN")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String email;
    private String password;

    public Admin() {
    }

    public Admin(AdminDTO dto) {
        this.id = dto.getId();
        this.nombre = dto.getNombre();
        this.email = dto.getEmail();
    }
}
