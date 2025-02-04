package com.cloud.paciente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.paciente.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByNombreContaining(String nombre);
}
