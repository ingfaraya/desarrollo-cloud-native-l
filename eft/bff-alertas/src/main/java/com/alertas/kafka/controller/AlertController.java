package com.alertas.kafka.controller;

import com.alertas.kafka.model.Alert;
import com.alertas.kafka.repository.AlertRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    // 🔹 Obtener todas las alertas con paginación y ordenación
    @GetMapping
    public Page<Alert> getAllAlerts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        return alertRepository.findAll(PageRequest.of(page, size, Sort.by(sort).descending()));
    }

    @GetMapping("/patientId/{patientId}")
    public ResponseEntity<?> getAlertsByPatientId(@PathVariable Long patientId) {
        List<Alert> alerts = alertRepository.findByPatientId(patientId);

        if (alerts.isEmpty()) {
            return ResponseEntity.status(404).body("No hay alertas para este paciente.");
        }

        return ResponseEntity.ok(alerts);
    }

    // 🔹 Crear una nueva alerta
    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        return alertRepository.save(alert);
    }

    // 🔹 Actualizar una alerta existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlert(@PathVariable Long id, @RequestBody Alert alertDetails) {
        return alertRepository.findById(id)
                .map(alert -> {
                    alert.setDescription(alertDetails.getDescription());
                    alert.setPriority(alertDetails.getPriority());
                    alert.setPatientId(alertDetails.getPatientId());
                    alert.setCreatedAt(alertDetails.getCreatedAt());
                    return ResponseEntity.ok(alertRepository.save(alert));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
