package com.consumidor.kafka.model;

import java.time.LocalDateTime;

public class VitalSign {
    private Long patientId;
    private String type;
    private double value;
    private LocalDateTime dateTime;

    public VitalSign() {}

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}
