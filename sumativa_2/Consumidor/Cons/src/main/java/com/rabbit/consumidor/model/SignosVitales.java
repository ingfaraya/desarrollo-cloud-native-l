package com.rabbit.consumidor.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class SignosVitales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signos_vitales_id")
    private Long id;

    private Double frecuenciaCardiaca; // Latidos por minuto
    private Double frecuenciaRespiratoria; // Respiraciones por minuto
    private Double presionSistolica; // mmHg
    private Double presionDiastolica; // mmHg
    private Double temperatura; // °C
    private Double saturacionOxigeno; // % SpO2

    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonBackReference
    private Paciente paciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(Double frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public Double getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(Double frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public Double getPresionSistolica() {
        return presionSistolica;
    }

    public void setPresionSistolica(Double presionSistolica) {
        this.presionSistolica = presionSistolica;
    }

    public Double getPresionDiastolica() {
        return presionDiastolica;
    }

    public void setPresionDiastolica(Double presionDiastolica) {
        this.presionDiastolica = presionDiastolica;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public void setSaturacionOxigeno(Double saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }

    
}
