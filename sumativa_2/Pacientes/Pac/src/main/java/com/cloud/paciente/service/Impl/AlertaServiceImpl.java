package com.cloud.paciente.service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.paciente.model.Alerta;
import com.cloud.paciente.model.SignosVitales;
import com.cloud.paciente.repository.AlertaRepository;
import com.cloud.paciente.service.AlertaService;

@Service
public class AlertaServiceImpl implements AlertaService {
    @Autowired
    private AlertaRepository alertaRepository;

    @Override
    public List<Alerta> evaluarCriticidad(SignosVitales signosVitales) {
        List<Alerta> alertas = new ArrayList<>();

        evaluarAlerta(alertas, signosVitales.getFrecuenciaCardiaca(), 50, 90, 120,
                "Frecuencia cardíaca", "bpm", "bradicardia", "frecuencia cardiaca elevada",
                "frecuencia cardiaca crítica", signosVitales);

        evaluarAlerta(alertas, signosVitales.getFrecuenciaRespiratoria(), 12, 16, 25,
                "Frecuencia respiratoria", "rpm", "bradipnea", "taquipnea moderada", "taquipnea severa", signosVitales);

        evaluarAlerta(alertas, signosVitales.getPresionSistolica(), 90, 120, 180,
                "Presión arterial sistólica", "mmHg", "hipotensión", "hipertensión moderada", "hipertensión severa",
                signosVitales);

        evaluarAlerta(alertas, signosVitales.getPresionDiastolica(), 60, 80, 120,
                "Presión arterial diastólica", "mmHg", "hipotensión", "hipertensión moderada", "hipertensión severa",
                signosVitales);

        evaluarAlerta(alertas, signosVitales.getTemperatura(), 35, 37.5, 39,
                "Temperatura", "°C", "hipotermia", "fiebre moderada", "hipertermia severa", signosVitales);

        evaluarAlerta(alertas, signosVitales.getSaturacionOxigeno(), 90, 95, 100,
                "Saturación de oxígeno", "%", "hipoxia leve", "hipoxia moderada", "hipoxia severa", signosVitales);

        return alertas.isEmpty() ? null : alertas;
    }

    /**
     * Método genérico para evaluar niveles de severidad en signos vitales
     */
    private void evaluarAlerta(List<Alerta> alertas, double valor, double limiteBajo, double limiteNormal,
            double limiteAlto,
            String tipoSigno, String unidad, String tipoLeve, String tipoModerado, String tipoCritico,
            SignosVitales signosVitales) {
        String mensaje = null;
        String tipo = null;
        String severidad = null;

        if (valor < limiteBajo) {
            mensaje = tipoSigno + " baja: " + valor + " " + unidad + ". Posible " + tipoLeve + ".";
            tipo = tipoLeve;
            severidad = "crítico";
        } else if (valor >= limiteAlto) {
            mensaje = tipoSigno + " muy elevada: " + valor + " " + unidad + ". Posible " + tipoCritico + ".";
            tipo = tipoCritico;
            severidad = "crítico";
        } else if (valor > limiteNormal) {
            mensaje = tipoSigno + " elevada: " + valor + " " + unidad + ". Posible " + tipoModerado + ".";
            tipo = tipoModerado;
            severidad = "moderado";
        }

        if (mensaje != null) {
            Alerta alerta = new Alerta();
            alerta.setMensaje(mensaje);
            alerta.setTipo(tipo);
            alerta.setSeveridad(severidad);
            alerta.setFechaGeneracion(LocalDateTime.now());
            alerta.setAtendida(false);
            alerta.setPaciente(signosVitales.getPaciente());
            alerta.setSignosVitales(signosVitales);
            alertas.add(alerta);
        }
    }

    @Override
    public Alerta guardarAlerta(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    @Override
    public List<Alerta> obtenerAlertasNoAtendidas() {
        return alertaRepository.findByAtendidaFalse();
    }

    @Override
    public List<Alerta> obtenerAlertasByPacienteID(Long pacienteID) {
        return alertaRepository.findByPacienteId(pacienteID);
    }

    @Override
    public Alerta marcarAlertaComoAtendida(Long alertaId) {
        // Buscar la alerta por ID
        Optional<Alerta> alertaOptional = alertaRepository.findById(alertaId);
        if (alertaOptional.isEmpty()) {
            return null;
        }

        // Marcar como atendida
        Alerta alerta = alertaOptional.get();
        alerta.setAtendida(true);

        // Guardar la alerta actualizada
        return alertaRepository.save(alerta);
    }
}
