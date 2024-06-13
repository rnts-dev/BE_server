package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.dto.AppointmentDTO;
import com.bside.backendapi.domain.appointment.dto.AppointmentRequest;
import com.bside.backendapi.domain.memberAppointment.service.UserApptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/appointment")
@Tag(name = "Appointment", description = "Appointment API Document")
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserApptService userApptService;

    @Operation(summary = "약속 생성", description = "약속 생성 메서드, 생성자 userappointment까지 생성")
    @PostMapping("/")
    public ResponseEntity<Long> createAppointment(@RequestBody AppointmentRequest appointmentRequest, HttpServletRequest httpRequest){

        //appointment 생성
        Long apid = appointmentService.createAppointment(appointmentRequest, httpRequest);
        log.info("Created appointment with ID: {}", apid);

        // userappointment를 생성해서
        userApptService.createUserAppt(apid,httpRequest);

        return ResponseEntity.ok().body(apid);
    }

    @Operation(summary = "약속 단일 조회", description = ".")
    @PostMapping("/searchSingleAppointment/{appointmentId}")
    public ResponseEntity<AppointmentDTO> searchSingleAppointment(@PathVariable("appointmentId") long appointmentId) {
        AppointmentDTO appointmentDTO = appointmentService.searchSingleAppointment(appointmentId);
        return ResponseEntity.ok(appointmentDTO);
    }

}
