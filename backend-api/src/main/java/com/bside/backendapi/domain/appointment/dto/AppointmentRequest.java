package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.entity.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class AppointmentRequest {

    private String title;
    private String appointmentType;
    private LocalDateTime time;
    private String place;
}
