package com.cloud.paciente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.paciente.model.SignosVitales;

public interface SignosVitalesRepository extends JpaRepository<SignosVitales, Long>{
    List<SignosVitales> findByPacienteId(Long pacienteId);
}
