package com.example.apiventas.api_ventas.controllers;

import com.example.apiventas.api_ventas.dto.LoginRequestDTO;
import com.example.apiventas.api_ventas.dto.RegistroClienteDTO;
import com.example.apiventas.api_ventas.models.Cliente;
import com.example.apiventas.api_ventas.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones de login de clientes")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login de cliente")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        boolean loginExitoso = authService.autenticarCliente(loginRequest.getEmail(), loginRequest.getPassword());
        if (loginExitoso) {
            return ResponseEntity.ok("Login exitoso");
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    
    @Operation(summary = "Registro de cliente")
    @PostMapping("/register")
    public ResponseEntity<Cliente> registrar(@RequestBody RegistroClienteDTO dto) {
        try {
            Cliente nuevo = authService.registrarCliente(dto);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}