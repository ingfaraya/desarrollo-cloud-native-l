package com.rabbit.productor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.productor.model.AlertaDTO;
import com.rabbit.productor.service.ProductorService;

@RestController
@RequestMapping("/api/alertas")
public class ProductorController {
    
    @Autowired
    private ProductorService productorService;

    @PostMapping
    public ResponseEntity<String> sendAlerta(@RequestBody AlertaDTO alerta){
        try {
            productorService.sendMessage(alerta);
            return ResponseEntity.ok("Alerta enviada a RabbitMQ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error enviando alerta: " + e.getMessage());
        }
    }
}
