package com.cloud.paciente.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.paciente.model.Alerta;
import com.cloud.paciente.model.SignosVitales;
import com.cloud.paciente.repository.SignosVitalesRepository;
import com.cloud.paciente.service.AlertaService;
import com.cloud.paciente.service.SignosVitalesService;

@Service
public class SignosVitalesServiceImpl implements SignosVitalesService{
    
    @Autowired
    private SignosVitalesRepository signosVitalesRepository;

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String PRODUCER_URL = "http://3.228.144.175:8080/api/alertas";

    @Override
    public SignosVitales registrarSignosVitales(SignosVitales signosVitales) {
        // Guardar signos vitales
        SignosVitales guardado = signosVitalesRepository.save(signosVitales);

        // Evaluar criticidad y generar múltiples alertas si es necesario
        List<Alerta> alertas = alertaService.evaluarCriticidad(guardado);
        if (alertas != null && !alertas.isEmpty()) {
            for (Alerta alerta : alertas) {
                enviarAlertaAlProductor(alerta);
            }
        }

        return guardado;
    }

    @Override
    public List<SignosVitales> obtenerSignosVitalesDePaciente(Long pacienteId) {
        return signosVitalesRepository.findByPacienteId(pacienteId);
    }

    /**
     * Metodo para consumir el servicio producotr para el rabbit
     * @param alerta
     */
    private void enviarAlertaAlProductor(Alerta alerta) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Alerta> request = new HttpEntity<>(alerta, headers);
            ResponseEntity<String> response = restTemplate.exchange(PRODUCER_URL, HttpMethod.POST, request, String.class);
            System.out.println("Alerta enviada al microservicio productor: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error enviando alerta al productor: " + e.getMessage());
        }
    }
}
