package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.vo.Title;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AppointmentResponse {

    private Long id;
    private Title title;
    private String appointmentType;
    private String customAppointmentTypeName;
    private String place;
    private LocalDateTime appointmentTime;

    public static AppointmentResponse of(final Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getTitle(),
                appointment.getAppointmentType().name(),
                null,
                appointment.getLocation().getPlace(),
                appointment.getAppointmentTime());
    }

    public static AppointmentResponse of(final Appointment appointment, CustomAppointmentType customAppointmentType) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getTitle(),
                null,
                customAppointmentType.getTypeName(),
                appointment.getLocation().getPlace(),
                appointment.getAppointmentTime());
    }
}