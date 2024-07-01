package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.persist.CustomAppointmentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomAppointmentTypeCreateRequest {

    @Valid @JsonProperty("typeName")
    private String typeName;

    @Valid @JsonProperty("image")
    private String image;

    public static CustomAppointmentTypeCreateRequest of(final CustomAppointmentType customAppointmentType) {
        return new CustomAppointmentTypeCreateRequest(
                customAppointmentType.getTypeName(),
                customAppointmentType.getImage());
    }

    @Builder
    public CustomAppointmentType toEntity() {
        return CustomAppointmentType.builder()
                .typeName(typeName)
                .image(image)
                .build();
    }
}
