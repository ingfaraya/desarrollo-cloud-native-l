package com.cloud.paciente.service;

import java.util.List;
import java.util.Optional;

import com.cloud.paciente.model.Paciente;

public interface PacienteService {
    List<Paciente> obtenerTodos();
    Optional<Paciente> obtenerPorId(Long id);
    Paciente crear(Paciente paciente);
    Paciente actualizar(Long id, Paciente paciente);
    void eliminar(Long id);
    List<Paciente> buscarPorNombre(String nombre);
}
