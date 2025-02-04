package com.cloud.paciente.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.paciente.model.Paciente;
import com.cloud.paciente.service.PacienteService;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> getAllPacientes(){
        return pacienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPacienteById(@PathVariable Long id){
        Optional<Paciente> paciente = pacienteService.obtenerPorId(id);
        if (paciente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun Paciente con ese ID"));
        }

        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    public ResponseEntity<Object> crearPaciente(@RequestBody Paciente paciente){
        Paciente p = pacienteService.crear(paciente);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear el Paciente"));
        }

        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente){
        Optional<Paciente> pacienteTemp = pacienteService.obtenerPorId(id);
        if (pacienteTemp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun Paciente con ese ID"));
        }

        Paciente p = pacienteService.actualizar(id, paciente);

        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarPaciente(@PathVariable Long id){
        Optional<Paciente> pacienteTemp = pacienteService.obtenerPorId(id);
        if (pacienteTemp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun Paciente con ese ID"));
        }

        pacienteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    static class ErrorResponse {
        private final String message;
    
        public ErrorResponse(String message){
            this.message = message;
        }
    
        public String getMessage(){
            return message;
        }
        
    }
}
