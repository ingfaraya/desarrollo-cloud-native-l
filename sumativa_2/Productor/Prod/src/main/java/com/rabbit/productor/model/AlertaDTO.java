package com.rabbit.productor.model;

import java.time.LocalDateTime;

public class AlertaDTO {
    private Long id;
    private String mensaje;
    private String tipo;
    private String severidad;
    private Boolean atendida;
    private LocalDateTime fechaGeneracion;
    private PacienteDTO paciente;
    private SignosVitalesDTO signosVitales;

    public AlertaDTO() {}

    public AlertaDTO(Long id, String mensaje, String tipo, String severidad, Boolean atendida, LocalDateTime fechaGeneracion, PacienteDTO paciente, SignosVitalesDTO signosVitales) {
        this.id = id;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.severidad = severidad;
        this.atendida = atendida;
        this.fechaGeneracion = fechaGeneracion;
        this.paciente = paciente;
        this.signosVitales = signosVitales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public Boolean getAtendida() {
        return atendida;
    }

    public void setAtendida(Boolean atendida) {
        this.atendida = atendida;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public SignosVitalesDTO getSignosVitales() {
        return signosVitales;
    }

    public void setSignosVitales(SignosVitalesDTO signosVitales) {
        this.signosVitales = signosVitales;
    }

    @Override
    public String toString() {
        return "AlertaDTO{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", tipo='" + tipo + '\'' +
                ", severidad='" + severidad + '\'' +
                ", atendida=" + atendida +
                ", fechaGeneracion=" + fechaGeneracion +
                ", paciente=" + paciente +
                ", signosVitales=" + signosVitales +
                '}';
    }
}
