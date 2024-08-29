package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeResponse;
import com.bside.backendapi.domain.appointment.exception.CustomAppointmentTypeNotFoundException;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.appointment.repository.CustomAppointmentTypeRepository;
import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
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

    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;
    private final MemberRepository memberRepository;
    private final AppointmentRepository appointmentRepository;

    public CustomAppointmentTypeResponse create(final CustomAppointmentType customAppointmentType, final CustomOAuth2User principal) {
        // 사용자 : 사용자 정의 약속 유형 매핑
        customAppointmentType.addMember(getMember(principal));

        customAppointmentTypeRepository.save(customAppointmentType);

        return CustomAppointmentTypeResponse.of(customAppointmentType);
    }

    public List<CustomAppointmentTypeResponse> getCustomAppointmentTypeList(final Long memberId) {
        return customAppointmentTypeRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::mapToCustomAppointmentTypeResponse)
                .collect(Collectors.toList());
    }

    public CustomAppointmentTypeResponse update(final CustomAppointmentType newCustomAppointmentType, final Long customAppointmentTypeId) {
        CustomAppointmentType customAppointmentType = getCustomAppointmentType(customAppointmentTypeId);
        customAppointmentType.update(newCustomAppointmentType);

        return CustomAppointmentTypeResponse.of(customAppointmentType);
    }

    public void delete(final Long customAppointmentTypeId) {
        // 사용자 정의 약속 유형 삭제
        if (getCustomAppointmentType(customAppointmentTypeId) != null) {
            customAppointmentTypeRepository.deleteById(customAppointmentTypeId);
        }

        // 삭제한 사용자 정의 약속 유형을 사용한 약속 처리
        // customAppointmentId -> null, appointmentType -> DEFAULT
        Optional<Appointment> appointments = appointmentRepository.findAllByCustomAppointmentTypeId(customAppointmentTypeId);
        appointments.ifPresent(Appointment::deletedCustomAppointmentType);
    }

    public Member getMember(final CustomOAuth2User principal) {
        return memberRepository.findByLoginId(LoginId.from(principal.getUsername()))
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public CustomAppointmentType getCustomAppointmentType(final Long customAppointmentTypeId) {
        return customAppointmentTypeRepository.findById(customAppointmentTypeId)
                .orElseThrow(() -> new CustomAppointmentTypeNotFoundException(ErrorCode.CUSTOM_TYPE_NOT_FOUND));
    }

    private CustomAppointmentTypeResponse mapToCustomAppointmentTypeResponse(final CustomAppointmentType customAppointmentType) {
        return CustomAppointmentTypeResponse.of(customAppointmentType);
    }
}
