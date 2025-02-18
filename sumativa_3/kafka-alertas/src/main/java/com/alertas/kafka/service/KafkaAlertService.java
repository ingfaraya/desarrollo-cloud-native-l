package com.alertas.kafka.service;

import com.alertas.kafka.model.Alert;
import com.alertas.kafka.repository.AlertRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaAlertService {

    private final AlertRepository alertRepository;

    public KafkaAlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @KafkaListener(topics = "senales_vitales", groupId = "grupo-procesador")
    public void saveAlert(String message) {
        Alert alert = new Alert();
        alert.setData(message);
        alertRepository.save(alert);
        System.out.println("🔴 Alerta guardada en BD: " + message);
    }
}