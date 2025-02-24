package com.alertas.kafka.service;

import com.alertas.kafka.model.Alert;
import com.alertas.kafka.model.VitalSign;
import com.alertas.kafka.repository.AlertRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // 1) Importar
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaAlertService {

    private final AlertRepository alertRepository;
    // 2) Registrar el módulo para LocalDateTime
    private final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    public KafkaAlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @KafkaListener(topics = "senales_vitales", groupId = "grupo-procesador")
    public void saveAlert(String message) {
        try {
            // 3) Deserializar el JSON a VitalSign
            VitalSign vitalSign = objectMapper.readValue(message, VitalSign.class);

            // 4) Construir el objeto Alert
            Alert alert = new Alert();
            alert.setDescription("Señal: " + vitalSign.getType() + " valor: " + vitalSign.getValue());
            alert.setCreatedAt(LocalDateTime.now());
            alert.setPatientId(vitalSign.getPatientId());

            // 5) Lógica para prioridad
            if ("heartRate".equalsIgnoreCase(vitalSign.getType()) && vitalSign.getValue() > 100) {
                alert.setPriority("ALTA");
            } else {
                alert.setPriority("MEDIA");
            }

            // 6) Guardar en BD
            alertRepository.save(alert);

            System.out.println("🔴 Alerta guardada en BD para " + vitalSign.getType()
                + ": " + vitalSign.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
