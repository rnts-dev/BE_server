package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.appointment.error.AppointmentMissMatchException;
import com.bside.backendapi.domain.appointment.error.AppointmentNotFoundException;
import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.appointmentMember.domain.repository.AppointmentMemberRepository;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final MemberRepository memberRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMemberRepository appointmentMemberRepository;

    public Long create(final Appointment appointment, final Long memberId){
        Appointment savedAppointment = appointmentRepository.save(appointment.create(memberId));
        Member member = getMemberEntity(memberId);

        appointmentMemberRepository.save(buildAppointmentMember(appointment, member));

        return savedAppointment.getId();
    }

    public void update(final Appointment updateAppointment, final Long appointmentId) {
        Appointment appointment = getAppointmentEntity(appointmentId);
        appointment.update(updateAppointment);
    }

    public void delete(final Long appointmentId, final Long memberId) {
        Appointment appointment = getAppointmentEntity(appointmentId);

        if (!Objects.equals(appointment.getCreatorId(), memberId)) {
            throw new AppointmentMissMatchException(ErrorCode.APPOINTMENT_MISS_MATCH);
        }

        appointment.delete();
    }

    public void invite(final Long appointmentId, final Long memberId) {
        Appointment appointment = getAppointmentEntity(appointmentId);
        Member invitedMember = getMemberEntity(memberId);

        appointmentMemberRepository.save(buildAppointmentMember(appointment, invitedMember));
    }

    // build appointment member
    public AppointmentMember buildAppointmentMember(final Appointment appointment, final Member member) {
        return AppointmentMember.builder()
                .appointment(appointment)
                .member(member)
                .build();
    }

    // get appoointment
    public Appointment getAppointmentEntity(final Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(ErrorCode.APPOINTMENT_NOT_FOUND));
    }

    // get member
    public Member getMemberEntity(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

}