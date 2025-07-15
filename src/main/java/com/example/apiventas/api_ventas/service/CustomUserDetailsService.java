package com.example.apiventas.api_ventas.service;

import com.example.apiventas.api_ventas.models.Admin;
import com.example.apiventas.api_ventas.models.Cliente;
import com.example.apiventas.api_ventas.repository.AdminRepository;
import com.example.apiventas.api_ventas.repository.ClienteRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService(AdminRepository adminRepository, ClienteRepository clienteRepository) {
        this.adminRepository = adminRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOpt = adminRepository.findByEmail(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(username);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword())
                    .roles("CLIENTE")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}
