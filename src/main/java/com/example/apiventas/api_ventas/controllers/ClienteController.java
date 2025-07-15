package com.example.apiventas.api_ventas.controllers;

import com.example.apiventas.api_ventas.dto.ClienteDTO;
import com.example.apiventas.api_ventas.models.Cliente;
import com.example.apiventas.api_ventas.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes")
    @GetMapping("/admin/clientes")
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteService.listarClientes().stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Obtener un cliente por ID")
    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable("id") Integer id) {
        return clienteService.obtenerClientePorId(id)
                .map(cliente -> ResponseEntity.ok(new ClienteDTO(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un cliente")
    @PutMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable("id") Integer id, @RequestBody ClienteDTO dto) {
        Cliente actualizado = clienteService.actualizarCliente(id, new Cliente(dto));
        return ResponseEntity.ok(new ClienteDTO(actualizado));
    }

    @Operation(summary = "Eliminar un cliente por ID")
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable("id") Integer id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}