package com.bside.backendapi.domain.appointmentMember.domain.repository;

import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentMemberRepository extends JpaRepository<AppointmentMember, Long> {
}
