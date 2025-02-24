package com.consumidor.kafka.service;

import com.consumidor.kafka.model.VitalSign;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "senales_vitales", groupId = "grupo-procesador")
    public void consumeAndProcess(ConsumerRecord<String, String> record) throws Exception {
        // 1. Deserializar el JSON a VitalSign
        VitalSign vitalSign = objectMapper.readValue(record.value(), VitalSign.class);

        // 2. Lógica para determinar si enviamos alerta
        // Ejemplo: si heartRate < 60 o > 100 => reenviar al tópico "alertas"
        if ("heartRate".equalsIgnoreCase(vitalSign.getType())) {
            double heartRate = vitalSign.getValue();
            if (heartRate < 60 || heartRate > 100) {
                // Reenviar el mismo JSON al tópico "alertas"
                kafkaTemplate.send("alertas", record.value());
                System.out.println("⚠ ALERTA: Señal anómala detectada " + record.value());
            }
        } else {
            // Si es "bloodPressure" o "temperature", decides la lógica
            // Por ejemplo:
            if ("bloodPressure".equalsIgnoreCase(vitalSign.getType()) && vitalSign.getValue() > 140) {
                kafkaTemplate.send("alertas", record.value());
                System.out.println("⚠ ALERTA: Presión alta " + record.value());
            }
        }
    }
}
