package com.cloud.paciente.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.paciente.model.Paciente;
import com.cloud.paciente.repository.PacienteRepository;
import com.cloud.paciente.service.PacienteService;

@Service
public class PacienteServiceImpl implements PacienteService{
    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> obtenerPorId(Long id) {
       return pacienteRepository.findById(id);
    }

    @Override
    public Paciente crear(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente actualizar(Long id, Paciente paciente) {
        if (pacienteRepository.existsById(id)) {
            paciente.setId(id);
            return pacienteRepository.save(paciente);
        }else{
            return null;
        }

    }

    @Override
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        return pacienteRepository.findByNombreContaining(nombre);
    }
    
    
}
