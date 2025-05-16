package com.example.apiventas.api_ventas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";  // Aquí debes tener un login.html en /src/main/resources/templates/
    }
}