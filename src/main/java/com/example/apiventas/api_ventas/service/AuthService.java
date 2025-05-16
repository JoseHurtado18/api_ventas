package com.example.apiventas.api_ventas.service;

import com.example.apiventas.api_ventas.dto.RegistroClienteDTO;
import com.example.apiventas.api_ventas.models.Cliente;
import com.example.apiventas.api_ventas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

    AuthService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
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

  public boolean autenticarCliente(String email, String password) {
      Optional<Cliente> cliente = clienteRepository.findByEmail(email);
      return cliente.isPresent() && passwordEncoder.matches(password, cliente.get().getPassword());
  }
}