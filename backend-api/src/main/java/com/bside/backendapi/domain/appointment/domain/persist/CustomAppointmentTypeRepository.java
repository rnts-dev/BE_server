package com.bside.backendapi.domain.appointment.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomAppointmentTypeRepository extends JpaRepository<CustomAppointmentType, Long> {

    Optional<CustomAppointmentType> findByMemberIdAndTypeName(Long memberId, String typeName);

    List<CustomAppointmentType> findAllByMemberId(Long memberId);

}
