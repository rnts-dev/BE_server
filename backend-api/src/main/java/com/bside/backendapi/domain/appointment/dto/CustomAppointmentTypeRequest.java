package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomAppointmentTypeRequest {

    private String typeName;
    private String imageUrl;

    public CustomAppointmentType toEntity() {
        return CustomAppointmentType.builder()
                .typeName(typeName)
                .imageUrl(imageUrl)
                .build();
    }
}
