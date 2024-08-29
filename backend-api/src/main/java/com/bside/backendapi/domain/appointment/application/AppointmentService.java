package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.domain.AppointmentMember;
import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.domain.appointment.exception.AppointmentNotFoundException;
import com.bside.backendapi.domain.appointment.exception.CustomAppointmentTypeNotFoundException;
import com.bside.backendapi.domain.appointment.repository.AppointmentMemberRepository;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.appointment.repository.CustomAppointmentTypeRepository;
import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.exception.MemberNotFoundException;
import com.bside.backendapi.domain.member.repository.MemberRepository;
import com.bside.backendapi.domain.member.vo.LoginId;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final MemberRepository memberRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMemberRepository appointmentMemberRepository;
    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;

    public AppointmentResponse create(final Appointment appointment, final CustomOAuth2User principal) {

        Appointment newAppointment = appointment.create(appointment);
        appointmentRepository.save(newAppointment);

        saveAppointmentMember(newAppointment, principal);

        // 사용자 정의 약속 유형의 ID가 있는 경우 해당 유형 이름과 함께 약속 반환
        if (appointment.getCustomAppointmentTypeId() != null) {
            CustomAppointmentType customAppointmentType = customAppointmentTypeRepository.findById(appointment.getCustomAppointmentTypeId())
                    .orElseThrow(() -> new CustomAppointmentTypeNotFoundException(ErrorCode.CUSTOM_TYPE_NOT_FOUND));
            return AppointmentResponse.of(newAppointment, customAppointmentType);
        }
        else
            return AppointmentResponse.of(newAppointment);
    }

    public void delete(final Long appointmentId, final CustomOAuth2User principal) {
        appointmentMemberRepository.deleteByAppointmentIdAndMemberId(appointmentId, getMember(principal).getId());
        appointmentRepository.deleteById(appointmentId);
    }

    public void acceptInvite(final Long appointmentId, final CustomOAuth2User principal) {
        saveAppointmentMember(getAppointment(appointmentId), principal);
    }

    public void udpate(final Appointment updateAppointment, final Long appointmentId) {
        Appointment appointment = getAppointment(appointmentId);
        appointment.update(updateAppointment);
    }

    private void saveAppointmentMember(final Appointment appointment, final CustomOAuth2User principal) {
        Member member = getMember(principal);

        AppointmentMember newAppointmentMember = AppointmentMember.builder()
                .appointment(appointment).member(member).build();

        appointmentMemberRepository.save(newAppointmentMember);
    }

    public Appointment getAppointment(final Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(ErrorCode.APPOINTMENT_NOT_FOUND));
    }

    private Member getMember(final CustomOAuth2User principal) {
        return memberRepository.findByLoginId(LoginId.from(principal.getUsername()))
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}