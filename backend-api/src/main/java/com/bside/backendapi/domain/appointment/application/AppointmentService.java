//package com.bside.backendapi.domain.appointment.application;
//
//import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
//import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
//import com.bside.backendapi.domain.appointment.dto.AppointmentDTO;
//import com.bside.backendapi.domain.appointment.dto.AppointmentRequest;
//import com.bside.backendapi.domain.appointment.mapper.AppointmentMapper;
//import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
//import com.bside.backendapi.global.jwt.util.JwtUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class AppointmentService {
//
//    private final AppointmentMapper appointmentMapper;
//    private final AppointmentRepository appointmentRepository;
//    private final MemberRepository memberRepository;
//    private final JwtUtil jwtUtil;
//
//    public Long createAppointment(AppointmentRequest request, HttpServletRequest httpRequest){
//
////        User user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
//
//        Appointment appointment = appointmentMapper.toEntity(request);
//        //appointment 저장
//        Appointment saveAppointment = appointmentRepository.save(appointment);
//
//        //생성된 appointmentid 반환
//        return saveAppointment.getId();
//    }
//
//    public AppointmentDTO searchSingleAppointment(long appointmentId) {
//        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
//        return AppointmentDTO.toDTO(appointment);
//    }
//}