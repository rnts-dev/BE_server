package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.domain.vo.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    @NotNull(message = "Location은 필수값입니다.")
    private Location location;


    // 정적 팩토리 메서드 추가
    public static AppointmentCreateRequest of(final Appointment appointment) {
        return new AppointmentCreateRequest(appointment.getTitle(), appointment.getAppointmentType(),
                appointment.getAppointmentTime(), appointment.getLocation());
    }

    @Builder
    public Appointment toEntity(Long creatorId) {
        return Appointment.builder()
                .title(title)
                .creatorId(creatorId)
                .appointmentType(appointmentType)
                .appointmentTime(appointmentTime)
                .location(location)
                .build();
    }
}