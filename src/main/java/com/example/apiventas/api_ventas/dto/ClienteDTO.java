package com.example.apiventas.api_ventas.dto;

import com.example.apiventas.api_ventas.models.Cliente;
import lombok.Data;

@Data
public class ClienteDTO {

    private Integer id;
    private String nombre;
    private String email;

    public ClienteDTO() {}

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.email = cliente.getEmail();
    }
}