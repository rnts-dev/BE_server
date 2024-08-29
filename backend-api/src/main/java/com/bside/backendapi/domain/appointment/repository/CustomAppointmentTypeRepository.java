package com.bside.backendapi.domain.appointment.repository;

import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomAppointmentTypeRepository extends JpaRepository<CustomAppointmentType, Long> {

    List<CustomAppointmentType> findAllByMemberId(Long memberId);

}
