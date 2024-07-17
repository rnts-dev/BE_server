package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.application.AppointmentViewService;
import com.bside.backendapi.domain.appointment.dto.AppointmentCreateRequest;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.domain.appointment.dto.AppointmentUpdateRequest;
import com.bside.backendapi.domain.appointment.dto.AppointmentViewResponse;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
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

    @Operation(summary = "약속 생성", description = "제목과 약속 정보를 이용하여 새로운 약속을 생성합니다.")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentCreateRequest appointmentCreateRequest){
        Long appointmentId = appointmentService.create(appointmentCreateRequest.toEntity(), this.getPrincipal().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(appointmentId));
    }

    @Operation(summary = "내 약속 모두 조회", description = "사용자가 포함된 모든 약속을 불러옵니다.")
    @GetMapping("/appointments/getAllAppointments")
    public ResponseEntity<List<AppointmentViewResponse>> getAllAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getAllAppointments(this.getPrincipal().getId()));
    }

    @Operation(summary = "지난 약속 조회", description = "현재 시각 기준으로 지난 약속을 불러옵니다.")
    @GetMapping("/appointments/getPastAppointments")
    public ResponseEntity<List<AppointmentViewResponse>> getPastAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getPastAppointments(this.getPrincipal().getId()));
    }

    @Operation(summary = "남은 약속 조회", description = "현재 시각 기준으로 남은 약속을 불러옵니다.")
    @GetMapping("/appointments/getRestAppointments")
    public ResponseEntity<List<AppointmentViewResponse>> getRestAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getRestAppointments(this.getPrincipal().getId()));
    }

    @Operation(summary = "약속 정보 수정", description = "해당 약속에 대한 정보를 수정할 수 있습니다.")
    @PatchMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest,
                                       @PathVariable Long appointmentId) {
        appointmentService.update(appointmentUpdateRequest.toEntity(), appointmentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "약속 빠지기",
            description = "해당 약속에서 사용자가 제외됩니다. 모든 사용자가 약속에서 빠질 경우, 자동으로 약속이 삭제됩니다.")
    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> delete(@PathVariable Long appointmentId) {
        appointmentService.delete(appointmentId, this.getPrincipal().getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "약속 초대 수락", description = "약속 초대를 수락할 경우 해당 약속에 사용자가 추가됩니다.")
    @PostMapping("/appointments/invite/{appointmentId}")
    public ResponseEntity<Void> invite(@PathVariable Long appointmentId) {
        appointmentService.invite(appointmentId, this.getPrincipal().getId());
        return ResponseEntity.ok().build();
    }

    private CustomOAuth2User getPrincipal() {
        return (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
