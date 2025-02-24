package com.alertas.kafka.controller;

import com.alertas.kafka.model.VitalSign;
import com.alertas.kafka.repository.VitalSignRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vitalsigns")
public class VitalSignController {

    private final VitalSignRepository vitalSignRepository;

    public VitalSignController(VitalSignRepository vitalSignRepository) {
        this.vitalSignRepository = vitalSignRepository;
    }

    // 🔹 Obtener todas las señales vitales con paginación y ordenación
    @GetMapping
    public Page<VitalSign> getAllVitalSigns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        return vitalSignRepository.findAll(PageRequest.of(page, size, Sort.by(sort).descending()));
    }

    // 🔹 Obtener una señal vital por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getVitalSignById(@PathVariable Long id) {
        Optional<VitalSign> vitalSign = vitalSignRepository.findById(id);
        return vitalSign.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Crear una nueva señal vital
    @PostMapping
    public VitalSign createVitalSign(@RequestBody VitalSign vitalSign) {
        return vitalSignRepository.save(vitalSign);
    }

    // 🔹 Actualizar una señal vital existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVitalSign(@PathVariable Long id, @RequestBody VitalSign vitalSignDetails) {
        return vitalSignRepository.findById(id)
                .map(vitalSign -> {
                    vitalSign.setType(vitalSignDetails.getType());
                    vitalSign.setValue(vitalSignDetails.getValue());
                    vitalSign.setPatientId(vitalSignDetails.getPatientId());
                    vitalSign.setDateTime(vitalSignDetails.getDateTime());
                    return ResponseEntity.ok(vitalSignRepository.save(vitalSign));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
