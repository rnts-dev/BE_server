package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.vo.Location;
import com.bside.backendapi.domain.appointment.vo.Title;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentRequest {

    @Valid
    private Title title;

    @Valid
    private AppointmentType appointmentType;

    @Valid
    private Long customAppointmentTypeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentTime;

    @Valid
    private Location location;

    public Appointment toEntity() {
        return Appointment.builder()
                .title(title)
                .appointmentType(appointmentType)
                .customAppointmentTypeId(customAppointmentTypeId)
                .appointmentTime(appointmentTime)
                .location(location)
                .build();
    }

    public static AppointmentRequest of(final Appointment appointment) {
        AppointmentType appointmentType = appointment.getAppointmentType();
        Long customAppointmentTypeId = appointment.getCustomAppointmentTypeId();

        return new AppointmentRequest(
                appointment.getTitle(),
                appointmentType,
                customAppointmentTypeId,
                appointment.getAppointmentTime(),
                appointment.getLocation()
        );
    }
}
