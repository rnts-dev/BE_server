package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.persist.CustomAppointmentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomAppointmentTypeViewResponse {

    private Long id;
    private String typeName;
    private String image;

    public static CustomAppointmentTypeViewResponse of(final CustomAppointmentType customAppointmentType) {
        return new CustomAppointmentTypeViewResponse(
                customAppointmentType.getId(),
                customAppointmentType.getTypeName(),
                customAppointmentType.getImage());
    }
}
