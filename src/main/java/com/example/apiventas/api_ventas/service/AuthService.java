package com.example.apiventas.api_ventas.service;

import com.example.apiventas.api_ventas.config.JwtUtil;
import com.example.apiventas.api_ventas.dto.RegistroAdminDTO;
import com.example.apiventas.api_ventas.dto.RegistroClienteDTO;
import com.example.apiventas.api_ventas.models.Admin;
import com.example.apiventas.api_ventas.models.Cliente;
import com.example.apiventas.api_ventas.repository.AdminRepository;
import com.example.apiventas.api_ventas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthService(ClienteRepository clienteRepository, AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.clienteRepository = clienteRepository;
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
    }

    public Cliente registrarCliente(RegistroClienteDTO dto) {
        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setPassword(passwordEncoder.encode(dto.getPassword()));

        return clienteRepository.save(cliente);
    }

    public AuthResponse autenticarYGenerarToken(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && passwordEncoder.matches(password, admin.get().getPassword())) {
            String token = jwtUtil.generateToken(email, "ADMIN", admin.get().getId());
            return new AuthResponse(token, "ADMIN", admin.get().getId());
        }

        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        if (cliente.isPresent() && passwordEncoder.matches(password, cliente.get().getPassword())) {
            String token = jwtUtil.generateToken(email, "CLIENTE", cliente.get().getId());
            return new AuthResponse(token, "CLIENTE", cliente.get().getId());
        }

        throw new RuntimeException("Credenciales inválidas");
    }

    public Admin registrarAdmin(RegistroAdminDTO dto) {
        if (adminRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado como admin");
        }

        Admin admin = new Admin();
        admin.setNombre(dto.getNombre());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));

        return adminRepository.save(admin);
    }

    // Clase auxiliar para devolver más datos en la respuesta
    public record AuthResponse(String token, String role, Integer id) {}
}
