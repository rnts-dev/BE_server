package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.vo.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AppointmentRequest {

    private Title title;
    private String appointmentType;
    private LocalDateTime time;
    private String latitude;
    private String longitude;
    private String place;
}
