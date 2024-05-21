package com.bside.backendapi.domain.appointment.controller;

import com.bside.backendapi.domain.appointment.dto.AppointmentRequest;
import com.bside.backendapi.domain.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping("/")
    public ResponseEntity<Objects> createAppointment(@RequestBody AppointmentRequest appointmentRequest){

        // userappointment를 생성해서 해당 id 리턴 해줘야 함
        return ResponseEntity.ok().build();
    }

}
