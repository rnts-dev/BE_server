package com.bside.backendapi.domain.appointmentMember.application;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.dto.AppointmentViewResponse;
import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.domain.appointmentMember.domain.repository.AppointmentMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentMemberService {

    private final AppointmentMemberRepository appointmentMemberRepository;

    public List<AppointmentViewResponse> getAllMyAppointment(final Long memberId) {
        return appointmentMemberRepository.findAllByMemberId(memberId)
                .stream()
                .map(appointmentMember -> convertToResponse(appointmentMember.getAppointment()))
                .collect(Collectors.toList());
    }

    public AppointmentViewResponse convertToResponse(final Appointment appointment) {
        return AppointmentViewResponse.of(appointment);
    }
}
