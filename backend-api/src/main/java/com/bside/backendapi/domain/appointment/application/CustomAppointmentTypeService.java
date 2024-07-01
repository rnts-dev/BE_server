package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.appointment.domain.persist.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.domain.persist.CustomAppointmentTypeRepository;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeViewResponse;
import com.bside.backendapi.domain.appointment.error.AppointmentExistException;
import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomAppointmentTypeService {

    private final MemberRepository memberRepository;
    private final AppointmentRepository appointmentRepository;
    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;

    public Long createCustomAppointmentType(final CustomAppointmentType customAppointmentType, final Long memberId) {
        // 사용자, 약속 유형 매핑
        customAppointmentType.addMember(getMemberEntity(memberId));

        CustomAppointmentType savedCustomAppointmentType = customAppointmentTypeRepository.save(customAppointmentType);

        return savedCustomAppointmentType.getId();
    }

    // get CustomAppointmentType
    public List<CustomAppointmentTypeViewResponse> getCustomAppointmentType(final Long memberId) {
        return customAppointmentTypeRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // delete
    public void delete(final Long customAppointmentTypeId) {
        Optional<Appointment> appointment = appointmentRepository.findByCustomAppointmentTypeId(customAppointmentTypeId);

        if (appointment.isPresent()) throw new AppointmentExistException(ErrorCode.APPOINTMENT_EXIST);

        customAppointmentTypeRepository.deleteById(customAppointmentTypeId);
    }

    // get member
    public Member getMemberEntity(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public CustomAppointmentTypeViewResponse convertToResponse(final CustomAppointmentType customAppointmentType) {
        return CustomAppointmentTypeViewResponse.of(customAppointmentType);
    }
}
