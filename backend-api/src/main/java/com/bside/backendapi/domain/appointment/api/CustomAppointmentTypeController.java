package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.CustomAppointmentTypeService;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeRequest;
import com.bside.backendapi.domain.appointment.dto.CustomAppointmentTypeResponse;
import com.bside.backendapi.global.common.ApiResponse;
import com.bside.backendapi.global.common.MessageContants;
import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
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
    @PostMapping("/custom-appointment-type")
    public ResponseEntity<CustomAppointmentTypeResponse> create(@Valid @RequestBody CustomAppointmentTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customAppointmentTypeService.create(request.toEntity(), this.getPrincipal()));
    }

    @Operation(summary = "사용자 정의 약속 유형 조회", description = "사용자가 정의한 약속 유형을 List로 반환합니다.")
    @GetMapping("/custom-appointment-types")
    public ResponseEntity<List<CustomAppointmentTypeResponse>> getCustomAppointmentType() {
        return ResponseEntity.ok().body(customAppointmentTypeService.getCustomAppointmentTypeList(this.getPrincipal().getId()));
    }

    @Operation(summary = "사용자 정의 약속 유형 수정")
    @PatchMapping("/custom-appointment-type/{customAppointmentTypeId}")
    public ResponseEntity<CustomAppointmentTypeResponse> update(@Valid @RequestBody CustomAppointmentTypeRequest request,
                                                                @PathVariable Long customAppointmentTypeId) {
        return ResponseEntity.ok().body(customAppointmentTypeService.update(request.toEntity(), customAppointmentTypeId));
    }

    @Operation(summary = "사용자 정의 약속 유형 삭제")
    @DeleteMapping("/custom-appointment-type/{customAppointmentTypeId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long customAppointmentTypeId) {
        customAppointmentTypeService.delete(customAppointmentTypeId);
        ApiResponse response = new ApiResponse(MessageContants.SUCCESS_DELETED);
        return ResponseEntity.ok(response);
    }

    public CustomOAuth2User getPrincipal() {
        return (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
