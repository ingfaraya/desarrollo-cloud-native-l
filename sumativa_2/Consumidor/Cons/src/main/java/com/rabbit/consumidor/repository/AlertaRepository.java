package com.rabbit.consumidor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabbit.consumidor.model.Alerta;


public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByAtendidaFalse();
    List<Alerta> findByPacienteId(Long pacienteId);
}
