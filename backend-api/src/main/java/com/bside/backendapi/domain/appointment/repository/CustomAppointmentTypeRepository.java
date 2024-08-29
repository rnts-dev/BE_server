package com.bside.backendapi.domain.appointment.repository;

import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomAppointmentTypeRepository extends JpaRepository<CustomAppointmentType, Long> {

    boolean existsByTypeName(final String typeName);

    Optional<CustomAppointmentType> findByMemberIdAndTypeName(final Long memberId, final String typeName);

    List<CustomAppointmentType> findAllByMemberId(final Long memberId);

}
