package com.alertas.kafka.repository;

import com.alertas.kafka.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByPatientId(Long patientId);
}
