package com.rabbit.productor.service.Impl;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbit.productor.model.SignosVitales;
import com.rabbit.productor.service.ProductorService;

@Service
public class ProductorServiceImpl implements ProductorService{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void enviarSignosVitales(List<SignosVitales> signosVitales) {
      rabbitTemplate.convertAndSend(signosVitales);
      System.out.println("Enviados " + signosVitales.size() + " signos vitales a RabbitMQ.");
    }

    
    
}
