package com.rabbit.productor.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rabbit.productor.model.SignosVitales;
import com.rabbit.productor.repository.SignosVitalesRepository;
import com.rabbit.productor.service.ProductorService;

@Component
public class VitalSignsScheduler {
    @Autowired
    private ProductorService productorService;

    @Autowired
    private SignosVitalesRepository signosVitalesRepository;

    // Ejecutar cada 5 minutos
    @Scheduled(fixedRate = 300000)
    // Ejeuctar cada 1 minuto
    // @Scheduled(fixedRate = 60000)
    public void programarEnvioSignosVitales() {
        List<SignosVitales> signosVitales = signosVitalesRepository.findAll();
        productorService.enviarSignosVitales(signosVitales);
    }
}
