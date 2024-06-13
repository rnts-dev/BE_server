package com.bside.backendapi.domain.appointment.mapper;

import com.bside.backendapi.domain.appointment.dto.AppointmentRequest;
import com.bside.backendapi.domain.appointment.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "userAppts", ignore = true)
    Appointment toEntity(AppointmentRequest dto);
}
