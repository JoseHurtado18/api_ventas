package com.example.apiventas.api_ventas.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}