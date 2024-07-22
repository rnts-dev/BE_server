package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.CustomAppointmentTypeService;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeCreateRequest;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeResponse;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeViewResponse;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CustomAppointmentType", description = "사용자 정의 약속 유형에 대한 API")
public class CustomAppointmentTypeController {

    private final CustomAppointmentTypeService customAppointmentTypeService;

    @Operation(summary = "사용자 정의 약속 유형 생성", description = "약속 유형(typeName)과 이미지(image)를 이용하여 사용자 정의 약속 유형을 생성합니다.")
    @PostMapping("/customAppointmentType")
    public ResponseEntity<CustomAppointmentTypeResponse> create(
            @Valid @RequestBody CustomAppointmentTypeCreateRequest customAppointmentTypeCreateRequest) {
        Long customAppointmentTypeId = customAppointmentTypeService.createCustomAppointmentType(
                        customAppointmentTypeCreateRequest.toEntity(), this.getPrincipal().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomAppointmentTypeResponse(customAppointmentTypeId));
    }

    @Operation(summary = "사용자 정의 약속 유형 조회", description = "사용자가 정의한 약속 유형을 List로 반환합니다.")
    @GetMapping("/customAppointmentType/getCustomAppointmentType")
    public ResponseEntity<List<CustomAppointmentTypeViewResponse>> getCustomAppointmentType() {
        return ResponseEntity.ok().body(customAppointmentTypeService.getCustomAppointmentType(this.getPrincipal().getId()));
    }

    @Operation(summary = "사용자 정의 약속 유형 삭제", description = "해당 약속 유형을 삭제합니다.")
    @DeleteMapping("/customAppointmentType/{customAppointmentTypeId}")
    public ResponseEntity<Void> delete(@PathVariable Long customAppointmentTypeId) {
        customAppointmentTypeService.delete(customAppointmentTypeId);
        return ResponseEntity.noContent().build();
    }

    public CustomOAuth2User getPrincipal() {
        return (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
