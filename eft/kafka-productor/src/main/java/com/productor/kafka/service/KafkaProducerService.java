package com.productor.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Random random = new Random();

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10000)  // 🔥 Ejecuta cada 1 segundo
    public void sendVitalSigns() {
        int heartRate = random.nextInt(40) + 60; // 60-100 bpm
        double temperature = 36 + random.nextDouble(); // 36-37°C
        int bloodPressure = 90 + random.nextInt(50); // 90-140 mmHg

        String message = "{ \"heartRate\": " + heartRate + 
                         ", \"temperature\": " + temperature + 
                         ", \"bloodPressure\": " + bloodPressure + " }";

        kafkaTemplate.send("senales_vitales", message);
        System.out.println("📡 Señales vitales enviadas: " + message);
    }
}
