package com.example.apiventas.api_ventas.service;

import com.example.apiventas.api_ventas.models.Cliente;
import com.example.apiventas.api_ventas.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Integer id) {
        return clienteRepository.findById(id);
    }

  
    public Optional<Cliente> obtenerClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }


    public Cliente actualizarCliente(Integer id, Cliente cliente) {
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }


    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
}