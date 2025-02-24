package com.alertas.kafka.controller;

import com.alertas.kafka.model.Patient;
import com.alertas.kafka.repository.PatientRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // 🔹 Obtener todos los pacientes con paginación y ordenación
    @GetMapping
    public ResponseEntity<Page<Patient>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        Page<Patient> patients = patientRepository.findAll(PageRequest.of(page, size, Sort.by(sort).descending()));
        return ResponseEntity.ok(patients);
    }

    // 🔹 Obtener un paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(patient -> ResponseEntity.ok().body((Object) patient)) // Se fuerza a Object para evitar conflicto de tipos
                .orElseGet(() -> ResponseEntity.status(404).body("Paciente no encontrado"));
    }

    // 🔹 Crear un nuevo paciente
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientRepository.save(patient));
    }

    // 🔹 Actualizar un paciente existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setFullName(patientDetails.getFullName());
                    patient.setIdentification(patientDetails.getIdentification());
                    patient.setBirthDate(patientDetails.getBirthDate());
                    patientRepository.save(patient);
                    return ResponseEntity.ok().body((Object) patient); // Se fuerza a Object para evitar conflicto de tipos
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Paciente no encontrado"));
    }

}
