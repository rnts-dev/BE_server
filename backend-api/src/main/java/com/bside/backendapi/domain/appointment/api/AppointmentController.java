package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.dto.AppointmentCreateRequest;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.domain.appointment.dto.AppointmentUpdateRequest;
import com.bside.backendapi.global.security.principal.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Appointment", description = "Appointment API Document")
public class AppointmentController {

    private final AppointmentService appointmentService;

    // create
    @Operation(summary = "약속 생성", description = "약속 생성 메서드, 생성자 userappointment까지 생성")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentCreateRequest appointmentCreateRequest){
        Long appointmentId = appointmentService.create(appointmentCreateRequest.toEntity(), getPrincipal().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(appointmentId));
    }

    // update
    @PatchMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest,
                                       @PathVariable Long appointmentId) {
        appointmentService.update(appointmentUpdateRequest.toEntity(), appointmentId, this.getPrincipal().getId());
        return ResponseEntity.ok().build();
    }

    // invite member

    // delete

    private CustomUserDetails getPrincipal() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
