package com.example.apiventas.api_ventas.controllers;

import com.example.apiventas.api_ventas.dto.LoginRequestDTO;
import com.example.apiventas.api_ventas.dto.RegistroClienteDTO;
import com.example.apiventas.api_ventas.models.Cliente;
import com.example.apiventas.api_ventas.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.apiventas.api_ventas.dto.RegistroAdminDTO;
import com.example.apiventas.api_ventas.models.Admin;

@RestController
@RequestMapping("/api")
@Tag(name = "Autenticación", description = "Operaciones de login y registro")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Iniciar sesion como administrador o cliente")
    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Credenciales para iniciar sesión",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n" +
                        "  \"email\": \"ana.gomez@example.com\",\n" +
                        "  \"password\": \"MiClaveSegura123\"\n" +
                        "}"
            )
        )
    )
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            AuthService.AuthResponse response = authService.autenticarYGenerarToken(
                    loginRequest.getEmail(), loginRequest.getPassword()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @Operation(summary = "Registro de cliente")
    @PostMapping("/registrarse")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos para registrar un nuevo usuario",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n" +
                        "  \"nombre\": \"Carlos Ramírez\",\n" +
                        "  \"email\": \"carlos.ramirez@example.com\",\n" +
                        "  \"password\": \"ContrasenaSegura456\"\n" +
                        "}"
            )
        )
    )
    public ResponseEntity<Cliente> registrarCliente(@RequestBody RegistroClienteDTO dto) {
        try {
            Cliente nuevo = authService.registrarCliente(dto);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Registro de administrador")
    @PostMapping("/admin/registrar")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos para registrar un nuevo administrador",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n" +
                        "  \"nombre\": \"Ana Gómez\",\n" +
                        "  \"email\": \"ana.gomez@example.com\",\n" +
                        "  \"password\": \"MiClaveSegura123\"\n" +
                        "}"
            )
        )
    )
    public ResponseEntity<Admin> registrarAdmin(@RequestBody RegistroAdminDTO dto) {
        try {
            Admin nuevo = authService.registrarAdmin(dto);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}