package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.application.AppointmentViewService;
import com.bside.backendapi.domain.appointment.application.CustomAppointmentTypeService;
import com.bside.backendapi.domain.appointment.dto.AppointmentCreateRequest;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.domain.appointment.dto.AppointmentUpdateRequest;
import com.bside.backendapi.domain.appointment.dto.AppointmentViewResponse;
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

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Appointment", description = "Appointment API Document")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentViewService appointmentViewService;

    // create
    @Operation(summary = "약속 생성", description = ".")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentCreateRequest appointmentCreateRequest){
        Long appointmentId = appointmentService.create(appointmentCreateRequest.toEntity(), this.getPrincipal().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(appointmentId));
    }

    // get all appointments
    @Operation(summary = "내 약속 모두 조회", description = ".")
    @GetMapping("/appointments/getAllAppointments")
    public ResponseEntity<List<AppointmentViewResponse>> getAllAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getAllAppointments(this.getPrincipal().getId()));
    }

    // get past appointments
    @Operation(summary = "지난 약속 조회", description = ".")
    @GetMapping("/appointments/getPastAppointments")
    public ResponseEntity<List<AppointmentViewResponse>> getPastAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getPastAppointments(this.getPrincipal().getId()));
    }

    // get rest appointments
    @Operation(summary = "지난 약속 조회", description = ".")
    @GetMapping("/appointments/getRestAppointments")
    public ResponseEntity<List<AppointmentViewResponse>> getRestAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getRestAppointments(this.getPrincipal().getId()));
    }

    // update
    @PatchMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest,
                                       @PathVariable Long appointmentId) {
        appointmentService.update(appointmentUpdateRequest.toEntity(), appointmentId);
        return ResponseEntity.ok().build();
    }

    // delete
    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> delete(@PathVariable Long appointmentId) {
        appointmentService.delete(appointmentId, this.getPrincipal().getId());
        return ResponseEntity.noContent().build();
    }

    // accept invitation
    @PostMapping("/appointments/invite/{appointmentId}")
    public ResponseEntity<Void> invite(@PathVariable Long appointmentId) {
        appointmentService.invite(appointmentId, this.getPrincipal().getId());
        return ResponseEntity.ok().build();
    }

    // cancel appointment
    @DeleteMapping("/appointments/cancel/{appointmentId}")
    public ResponseEntity<Void> cancel(@PathVariable Long appointmentId) {
        appointmentService.cancel(appointmentId, this.getPrincipal().getId());
        return ResponseEntity.noContent().build();
    }

    private CustomUserDetails getPrincipal() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
