package com.cloud.paciente.service;

import java.util.List;

import com.cloud.paciente.model.SignosVitales;

public interface SignosVitalesService {
    SignosVitales registrarSignosVitales(SignosVitales signosVitales);
    List<SignosVitales> obtenerSignosVitalesDePaciente(Long pacienteId);
}
