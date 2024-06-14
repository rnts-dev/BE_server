package com.bside.backendapi.domain.memberAppointment.domain.repository;

import com.bside.backendapi.domain.memberAppointment.domain.entity.MemberAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAppointmentRepository extends JpaRepository<MemberAppointment, Long> {
}
