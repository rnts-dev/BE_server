package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentViewService;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Appointment List", description = "Appointment API Document")
public class AppointmentViewController {

    private final AppointmentViewService appointmentViewService;

    @Operation(summary = "내 모든 약속 조회")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getAllAppointments(this.getPrincipal()));
    }

    @Operation(summary = "지난 약속 조회")
    @GetMapping("/appointments/past")
    public ResponseEntity<List<AppointmentResponse>> getPastAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getPastAppointments(this.getPrincipal()));
    }

    @Operation(summary = "남은 약속 조회")
    @GetMapping("/appointments/upcoming")
    public ResponseEntity<List<AppointmentResponse>> getUpcomingAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getUpcomingAppointments(this.getPrincipal()));
    }

    private CustomOAuth2User getPrincipal() {
        return (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
