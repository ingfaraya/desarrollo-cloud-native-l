package com.cloud.paciente.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.paciente.dto.SignosVitalesRequestDTO;
import com.cloud.paciente.model.Paciente;
import com.cloud.paciente.model.SignosVitales;
import com.cloud.paciente.service.PacienteService;
import com.cloud.paciente.service.SignosVitalesService;

@RestController
@RequestMapping("/api/signos-vitales")
public class SignosVitalesController {
    
    @Autowired
    private SignosVitalesService signosVitalesService;

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> registrarSignosVitales(@RequestBody SignosVitalesRequestDTO request) {
        // Validar si el paciente existe
        Optional<Paciente> pacienteOptional = pacienteService.obtenerPorId(request.getPacienteId());
        if (pacienteOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Paciente no encontrado con ID: " + request.getPacienteId());
        }

        // Crear la entidad SignosVitales con los datos del DTO
        SignosVitales signosVitales = new SignosVitales();
        signosVitales.setFrecuenciaCardiaca(request.getFrecuenciaCardiaca());
        signosVitales.setFrecuenciaRespiratoria(request.getFrecuenciaRespiratoria());
        signosVitales.setPresionSistolica(request.getPresionSistolica());
        signosVitales.setPresionDiastolica(request.getPresionDiastolica());
        signosVitales.setTemperatura(request.getTemperatura());
        signosVitales.setSaturacionOxigeno(request.getSaturacionOxigeno());
        signosVitales.setPaciente(pacienteOptional.get());

        // Registrar los signos vitales
        SignosVitales guardado = signosVitalesService.registrarSignosVitales(signosVitales);

        // Devolver la respuesta con los signos vitales registrados
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping("/paciente/{id}")
    public List<SignosVitales> obtenerSignosVitalesPaciente(@PathVariable Long id){
        return signosVitalesService.obtenerSignosVitalesDePaciente(id);
    }
}
