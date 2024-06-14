package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentCreateRequest {

    @Valid
    @NotNull(message = "title은 필수값입니다.")
    private String title;

    @Valid
    @NotNull(message = "appointment_type은 필수값입니다.")
    private AppointmentType appointmentType;

    @Valid
    @NotNull(message = "appointment_time은 필수값입니다.")
    private LocalDateTime appointmentTime;

    @Valid
    @NotNull(message = "location은 필수값입니다.")
    private String location;

    @Valid
    @NotNull(message = "latitude은 필수값입니다.")
    private Double latitude;

    @Valid
    @NotNull(message = "longitude은 필수값입니다.")
    private Double longitude;

    public static AppointmentCreateRequest of(final Appointment appointment) {
        return new AppointmentCreateRequest(
                appointment.getTitle(), appointment.getAppointmentType(),
                appointment.getAppointmentTime(), appointment.getLocation(),
                appointment.getLatitude(), appointment.getLongitude());
    }

    @Builder
    public Appointment toEntity() {
        return Appointment.builder()
                .title(title)
                .appointmentType(appointmentType)
                .appointmentTime(appointmentTime)
                .location(location)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
