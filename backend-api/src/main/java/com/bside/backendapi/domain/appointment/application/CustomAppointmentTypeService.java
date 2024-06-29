package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.persist.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.domain.persist.CustomAppointmentTypeRepository;
import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomAppointmentTypeService {

    private final MemberRepository memberRepository;
    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;

    public void createCustomAppointmentType(final String typeName, final Long memberId) {
        CustomAppointmentType customAppointmentType = CustomAppointmentType.builder()
                .member(getMemberEntity(memberId))
                .typeName(typeName)
                .build();
        customAppointmentTypeRepository.save(customAppointmentType);
    }

    // get member
    public Member getMemberEntity(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
