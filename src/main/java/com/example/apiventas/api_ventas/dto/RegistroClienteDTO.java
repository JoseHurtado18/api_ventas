package com.example.apiventas.api_ventas.dto;

import lombok.Data;

@Data
public class RegistroClienteDTO {
    private String nombre;
    private String email;
    private String password;
}