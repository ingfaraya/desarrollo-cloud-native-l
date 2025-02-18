package com.consumidor.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "senales_vitales", groupId = "grupo-procesador")
    public void consumeAndProcess(ConsumerRecord<String, String> record) throws Exception {
        JsonNode data = objectMapper.readTree(record.value());
        int heartRate = data.get("heartRate").asInt();
        
        if (heartRate < 60 || heartRate > 100) {
            kafkaTemplate.send("alertas", record.value());
            System.out.println("⚠ ALERTA: Señal anómala detectada " + record.value());
        }
    }
}