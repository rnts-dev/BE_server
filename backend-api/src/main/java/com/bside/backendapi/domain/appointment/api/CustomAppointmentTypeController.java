package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.CustomAppointmentTypeService;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeCreateRequest;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeResponse;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeViewResponse;
import com.bside.backendapi.global.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CustomAppointmentTypeController {

    private final CustomAppointmentTypeService customAppointmentTypeService;

    // create
    @PostMapping("/customAppointmentType")
    public ResponseEntity<CustomAppointmentTypeResponse> create(
            @Valid @RequestBody CustomAppointmentTypeCreateRequest customAppointmentTypeCreateRequest) {
        Long customAppointmentTypeId = customAppointmentTypeService.createCustomAppointmentType(
                        customAppointmentTypeCreateRequest.toEntity(), this.getPrincipal().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomAppointmentTypeResponse(customAppointmentTypeId));
    }

    // get Custom Appointment Type
    @GetMapping("/customAppointmentType/getCustomAppointmentType")
    public ResponseEntity<List<CustomAppointmentTypeViewResponse>> getCustomAppointmentType() {
        return ResponseEntity.ok().body(customAppointmentTypeService.getCustomAppointmentType(this.getPrincipal().getId()));
    }

    public CustomUserDetails getPrincipal() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
