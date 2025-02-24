package com.alertas.kafka.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VITAL_SIGNS")
public class VitalSign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;      // ID de Paciente

    private String type;         // ej. "heartRate", "bloodPressure"

    private double value;        // ej. 120 (presión), 80 latidos/min

    private LocalDateTime dateTime;  // momento de la medición

    public VitalSign() {}

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
