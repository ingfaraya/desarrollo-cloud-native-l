package com.productor.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.productor.kafka.model.VitalSignPayload;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());
    private final Random random = new Random();

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10000)
    public void sendVitalSigns() {
        VitalSignPayload vs = new VitalSignPayload();
        vs.setPatientId(1L);
        vs.setType("heartRate");
        vs.setValue(random.nextInt(40) + 60);
        vs.setDateTime(LocalDateTime.now());

        try {
            // Serializar con el module JSR310
            String message = objectMapper.writeValueAsString(vs);
            kafkaTemplate.send("senales_vitales", message);
            System.out.println("Enviando: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
