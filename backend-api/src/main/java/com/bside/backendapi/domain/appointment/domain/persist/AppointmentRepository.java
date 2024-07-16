package com.bside.backendapi.domain.appointment.domain.persist;

import com.bside.backendapi.domain.member.domain.vo.LoginId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByCreatorId(LoginId loginId);

    Optional<Appointment> findByCustomAppointmentTypeId(Long customAppointmentTypeId);
}
