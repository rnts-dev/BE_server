package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.dto.AppointmentCreateRequest;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.domain.appointment.dto.AppointmentUpdateRequest;
import com.bside.backendapi.domain.appointment.dto.AppointmentViewResponse;
import com.bside.backendapi.domain.appointmentMember.application.AppointmentMemberService;
import com.bside.backendapi.domain.appointmentMember.dto.response.AppointmentMemberResponse;
import com.bside.backendapi.global.security.principal.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Appointment", description = "Appointment API Document")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMemberService appointmentMemberService;

    // create
    @Operation(summary = "약속 생성", description = "약속 생성 메서드, 생성자 userappointment까지 생성")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentCreateRequest appointmentCreateRequest){
        Long appointmentId = appointmentService.create(appointmentCreateRequest.toEntity(), this.getPrincipal().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(appointmentId));
    }

    // read
    @Operation(summary = "내 약속 모두 조회", description = ".")
    @GetMapping("/appointments/getAllMyAppointment")
    public ResponseEntity<List<AppointmentViewResponse>> getAllMyAppointment() {
        return ResponseEntity.ok().body(appointmentMemberService.getAllMyAppointment(this.getPrincipal().getId()));
    }

    // update
    @PatchMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest,
                                       @PathVariable Long appointmentId) {
        appointmentService.update(appointmentUpdateRequest.toEntity(), appointmentId, this.getPrincipal().getId());
        return ResponseEntity.ok().build();
    }

    // delete
    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> delete(@PathVariable Long appointmentId) {
        appointmentService.delete(appointmentId, this.getPrincipal().getId());
        return ResponseEntity.noContent().build();
    }

    private CustomUserDetails getPrincipal() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
