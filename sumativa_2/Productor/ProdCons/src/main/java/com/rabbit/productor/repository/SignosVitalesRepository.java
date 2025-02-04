package com.rabbit.productor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabbit.productor.model.SignosVitales;

public interface SignosVitalesRepository extends JpaRepository<SignosVitales, Long>{
    List<SignosVitales> findByPacienteId(Long pacienteId);
}

