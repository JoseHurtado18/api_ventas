package com.example.apiventas.api_ventas.dto;

import com.example.apiventas.api_ventas.models.Admin;
import lombok.Data;

@Data
public class AdminDTO {

    private Integer id;
    private String nombre;
    private String email;

    public AdminDTO() {}

    public AdminDTO(Admin admin) {
        this.id = admin.getId();
        this.nombre = admin.getNombre();
        this.email = admin.getEmail();
    }
}
