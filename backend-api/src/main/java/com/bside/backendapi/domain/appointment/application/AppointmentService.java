package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.appointmentMember.domain.repository.AppointmentMemberRepository;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final MemberRepository memberRepository;
    private final AppointmentMemberRepository appointmentMemberRepository;
    private final PenaltyRepository penaltyRepository;

    public Long create(final Appointment appointment, final Long memberId){
        Appointment savedAppointment = appointmentRepository.save(appointment.addMember(memberId));
        log.info("약속 종류 : {}", appointment.getAppointmentType());
        log.info("약속 시간 : {}", appointment.getAppointmentTime());
        log.info("약속 장소 : {}", appointment.getLocation().place());
        log.info("약속 위도 : {}", appointment.getLocation().latitude());
        log.info("약속 경도 : {}", appointment.getLocation().longitude());
        return savedAppointment.getId();
    }

}