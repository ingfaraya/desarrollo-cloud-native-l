package com.cloud.paciente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.paciente.model.Alerta;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByAtendidaFalse();
    List<Alerta> findByPacienteId(Long pacienteId);
}
