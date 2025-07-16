package com.example.apiventas.api_ventas.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequesicionesApiService {
    
    @Autowired
    private RestTemplate restTemplate;

    public String postAlerta(String nombre, String cantidad){
        
        String url = "";

        Map<String, String> request = new HashMap<>();
        request.put("nombre", nombre);
        request.put("cantidad", cantidad);

        return restTemplate.postForObject(url, request, String.class);
    }
}
