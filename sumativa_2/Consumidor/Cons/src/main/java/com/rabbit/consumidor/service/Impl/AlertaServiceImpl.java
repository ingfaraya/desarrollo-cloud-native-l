package com.rabbit.consumidor.service.Impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbit.consumidor.model.Alerta;
import com.rabbit.consumidor.repository.AlertaRepository;
import com.rabbit.consumidor.service.AlertaService;

@Service
public class AlertaServiceImpl implements AlertaService{

    @Autowired
    private AlertaRepository alertaRepository;


    @Override
    @RabbitListener(queues = "myQueue")
    public void guardarAlerta(Alerta alerta) {
        System.out.println("Se guarda la alerta en la base de datos!");
        alertaRepository.save(alerta);
    }
    
}
