package com.rabbit.consumidor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabbit.consumidor.model.Paciente;



public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByNombreContaining(String nombre);
}
