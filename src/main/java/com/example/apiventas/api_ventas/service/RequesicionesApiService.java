package com.example.apiventas.api_ventas.service;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RequesicionesApiService {
    
    @Autowired
    private RestTemplate restTemplate;

    
    /* public String consumirApi(String nombre, String cantidad) {
        String url = "https://api.externa.com/post-endpoint";

        // Objeto que vas a enviar en el POST
        Map<String, String> request = new HashMap<>();
        request.put("nombre", nombre);
        request.put("correo", cantidad);

        // Encabezados
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    } */

    public String postAlerta(String nombre, String cantidad){
        String url = "https://api.externa.com/post-endpoint";

        Map<String, String> request = new HashMap<>();
        request.put("nombre", nombre);
        request.put("cantidad", cantidad);

        return restTemplate.postForObject(url, request, String.class);
    }
}
