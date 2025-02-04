package com.rabbit.productor.service.Impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbit.productor.model.AlertaDTO;
import com.rabbit.productor.service.ProductorService;

@Service
public class ProductorServiceImpl implements ProductorService{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(AlertaDTO alerta) {
     try {
      rabbitTemplate.convertAndSend(alerta);
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
    
}
