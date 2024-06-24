package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.appointment.error.AppointmentNotFoundException;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.appointmentMember.domain.repository.AppointmentMemberRepository;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import com.bside.backendapi.global.error.exception.ErrorCode;
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
        return savedAppointment.getId();
    }

    public void update(final Appointment updateAppointment, final Long appointmentId, final Long memberId) {
        Appointment appointment = getAppointmentEntity(appointmentId);
        appointment.updateAppointment(updateAppointment);
    }

    // get Entity
    public Appointment getAppointmentEntity(final Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(ErrorCode.APPOINTMENT_NOT_FOUND));
    }
}