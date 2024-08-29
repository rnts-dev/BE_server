package com.bside.backendapi.domain.appointment.repository;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findAllByCustomAppointmentTypeId(final Long customAppointmentTypeId);
}
