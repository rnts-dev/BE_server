package com.bside.backendapi.domain.appointment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomAppointmentTypeResponse {

    private Long customAppointmentTypeId;
}
