package com.bside.backendapi.domain.appointmentMember.domain.repository;

import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentMemberRepository extends JpaRepository<AppointmentMember, Long> {

    List<AppointmentMember> findAllByMemberId(Long memberId);

    void deleteByAppointmentIdAndMemberId(Long appointmentId, Long memberId);

    List<AppointmentMember> findAllByAppointmentId(Long appointmentId);
}
