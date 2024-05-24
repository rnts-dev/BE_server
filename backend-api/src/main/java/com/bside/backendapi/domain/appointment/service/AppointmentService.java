package com.bside.backendapi.domain.appointment.service;

import com.bside.backendapi.domain.appointment.dto.AppointmentRequest;
import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.appointment.mapper.AppointmentMapper;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public Long createAppointment(AppointmentRequest request){

        //현재 사용자
        Long userid = 2L;
        User user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));

        Appointment appointment = appointmentMapper.toEntity(request);
        //appointment 저장
        Appointment saveAppointment = appointmentRepository.save(appointment);

        //생성된 appointmentid 반환
        return saveAppointment.getId();
    }

}
