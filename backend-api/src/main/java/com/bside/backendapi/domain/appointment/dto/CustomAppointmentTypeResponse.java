package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomAppointmentTypeResponse {

    private String typeName;
    private String imageUrl;

    public static CustomAppointmentTypeResponse of(final CustomAppointmentType customAppointmentType) {
        return new CustomAppointmentTypeResponse(customAppointmentType.getTypeName(), customAppointmentType.getImageUrl());
    }
}
