package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.domain.vo.Location;
import com.bside.backendapi.domain.appointment.domain.vo.Title;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentViewResponse {

    private Long id;
    private Title title;
    private AppointmentType appointmentType;
    private String typeName;
    private LocalDateTime appointmentTime;
    private Location location;

    public static AppointmentViewResponse of(final Appointment appointment) {
        if (appointment.getCustomAppointmentType() == null) {
            return new AppointmentViewResponse(
                    appointment.getId(),
                    appointment.getTitle(),
                    appointment.getAppointmentType(),
                    null,
                    appointment.getAppointmentTime(),
                    appointment.getLocation());
        } else {
            return new AppointmentViewResponse(
                    appointment.getId(),
                    appointment.getTitle(),
                    null,
                    appointment.getCustomAppointmentType().getTypeName(),
                    appointment.getAppointmentTime(),
                    appointment.getLocation());
        }
    }
}