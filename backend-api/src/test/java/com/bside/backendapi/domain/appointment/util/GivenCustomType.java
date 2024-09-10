package com.bside.backendapi.domain.appointment.util;

import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.util.GivenMember;

public class GivenCustomType {

    public static Member GIVEN_MEMBER = GivenMember.toEntity();
    public static String GIVEN_TYPENAME = "테스트모임";
    public static String GIVEN_IMAGEURL = "테스트 URL";

    public static CustomAppointmentType toEntity() {
        return CustomAppointmentType.builder()
                .id(1L)
                .member(GIVEN_MEMBER)
                .typeName(GIVEN_TYPENAME)
                .imageUrl(GIVEN_IMAGEURL)
                .build();
    }

}
