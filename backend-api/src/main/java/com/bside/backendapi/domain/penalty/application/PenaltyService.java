package com.bside.backendapi.domain.penalty.application;

//import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
//import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
//import com.bside.backendapi.domain.penalty.dto.ReceivedPenaltyDTO;
//import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
//import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
//import com.bside.backendapi.domain.penalty.domain.ReceivedPenalty;
//import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
//import com.bside.backendapi.domain.penalty.domain.ReceivedPenaltyRepository;
//import com.bside.backendapi.domain.member.entity.User;
//import com.bside.backendapi.domain.member.repository.UserRepository;
//import com.bside.backendapi.domain.memberAppointment.entity.UserAppt;
//import com.bside.backendapi.domain.memberAppointment.repository.UserApptRepository;
//import com.bside.backendapi.global.jwt.util.JwtUtil;
import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
//
@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;

    public Long create(final Penalty penalty, final Long memberId){
        Penalty savedPenalty = penaltyRepository.save(penalty.addPenaltyCreatorId(memberId));//set쓰지말고
        return savedPenalty.getId();
    }




}
