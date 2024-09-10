package com.bside.backendapi.domain.appointmentMember.domain.repository;

import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentMemberRepository extends JpaRepository<AppointmentMember, Long> {

    List<AppointmentMember> findAllByMemberId(Long memberId);

    void deleteByAppointmentIdAndMemberId(Long appointmentId, Long memberId);

    List<AppointmentMember> findAllByAppointmentId(Long appointmentId);

    // N + 1 문제를 해결하기 위한 join fetch
    @Query("SELECT am FROM AppointmentMember am JOIN FETCH am.appointment WHERE am.member.id = :memberId")
    List<AppointmentMember> findAllWithAppointmentsByMemberId(Long memberId);
}
