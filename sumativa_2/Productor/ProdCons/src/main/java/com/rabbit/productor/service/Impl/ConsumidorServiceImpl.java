package com.rabbit.productor.service.Impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.productor.model.SignosVitales;
import com.rabbit.productor.service.ConsumidorService;

@Service
public class ConsumidorServiceImpl implements ConsumidorService{

    @Value("${json.output.directory}")
    private String outputDirectory;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @RabbitListener(queues = "myQueueReporteria")
    public void recibirSignosVitales(List<SignosVitales> signosVitales) {
        try {
            // Crear el directorio si no existe
            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generar el archivo JSON
            File archivo = new File(directory, "signos_vitales_" + System.currentTimeMillis() + ".json");
            objectMapper.writeValue(archivo, signosVitales);

            System.out.println("Archivo JSON generado en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al generar el archivo JSON: " + e.getMessage());
        }
    }
    
}
