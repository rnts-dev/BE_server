package com.bside.backendapi.domain.appointment.repository;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment findAppointmentByUserApptsId(Long userApptsId);

}
