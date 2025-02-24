package com.alertas.kafka.repository;

import com.alertas.kafka.model.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {
    List<VitalSign> findByPatientId(Long patientId);
}
