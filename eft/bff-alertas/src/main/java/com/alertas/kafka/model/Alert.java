package com.alertas.kafka.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTS")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ejemplo de contenido textual de la alerta
    private String description;

    // Nivel de prioridad: BAJA, MEDIA, ALTA
    private String priority;

    // Fecha/hora cuando se creó la alerta
    private LocalDateTime createdAt;

    // Relación a un paciente, si quisieras anclar la alerta a un "Patient"
    private Long patientId;

    // Constructors
    public Alert() { }

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
}
