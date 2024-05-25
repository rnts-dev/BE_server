package com.bside.backendapi.domain.appointment.service;

import com.bside.backendapi.domain.appointment.dto.AppointmentRequest;
import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.appointment.mapper.AppointmentMapper;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.user.repository.UserRepository;
import com.bside.backendapi.global.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppointmentService {

    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Long createAppointment(AppointmentRequest request, HttpServletRequest httpRequest){

//        User user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));

        Appointment appointment = appointmentMapper.toEntity(request);
        //appointment 저장
        Appointment saveAppointment = appointmentRepository.save(appointment);

        //생성된 appointmentid 반환
        return saveAppointment.getId();
    }

    public Appointment searchSingleAppointment(long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow();
    }
}
