package com.bside.backendapi.domain.appointment.util;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.domain.AppointmentMember;
import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.util.GivenMember;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class GivenAppointmentMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public static Long id;
    public static Appointment GIVEN_APPOINTMENT = GivenAppointment.toEntity();
    public static Member GIVEN_MEMBER = GivenMember.toEntity();

    public static AppointmentMember toEntity() {
        return AppointmentMember.builder()
                .appointment(GIVEN_APPOINTMENT)
                .member(GIVEN_MEMBER)
                .build();
    }
}