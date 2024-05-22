package com.bside.backendapi.domain.appointment.controller;

import com.bside.backendapi.domain.appointment.dto.AppointmentRequest;
import com.bside.backendapi.domain.appointment.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/appointment")
@Tag(name = "Appointment", description = "Appointment API Document")
public class AppointmentController {


    private AppointmentService appointmentService;
    @Operation(summary = "약속 생성", description = "약속 생성 메서드, 생성자 userappointment까지 생성")
    @PostMapping("/")
    public ResponseEntity<Objects> createAppointment(@RequestBody AppointmentRequest appointmentRequest){

        //appointment 생성

        // userappointment를 생성해서 해당 id 리턴 해줘야 함

        return ResponseEntity.ok().build();
    }

}
