package com.bside.backendapi.domain.penalty.application;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.penalty.dto.PenaltyDTO;
import com.bside.backendapi.domain.penalty.dto.ReceivedPenaltyDTO;
import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
import com.bside.backendapi.domain.penalty.domain.ReceivedPenalty;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import com.bside.backendapi.domain.penalty.domain.ReceivedPenaltyRepository;
import com.bside.backendapi.domain.member.entity.User;
import com.bside.backendapi.domain.member.repository.UserRepository;
import com.bside.backendapi.domain.memberAppointment.entity.UserAppt;
import com.bside.backendapi.domain.memberAppointment.repository.UserApptRepository;
import com.bside.backendapi.global.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {

    private final UserRepository userRepository;
    private final UserApptRepository userApptRepository;
    private final AppointmentRepository appointmentRepository;
    private final PenaltyRepository penaltyRepository;
    private final ReceivedPenaltyRepository receivedPenaltyRepository;
    private final JwtUtil jwtUtil;

    public void createPenalty(long uaid, PenaltyType penaltyType, String content, int fine) throws Exception {
        UserAppt userAppts = userApptRepository.findUserApptById(uaid);
        User user = userRepository.findById(userAppts.getUser().getId()).orElseThrow();
        Appointment appointment = appointmentRepository.findAppointmentByUserApptsId(userAppts.getId());

        Penalty penalty = penaltyRepository.findPenaltyByUser(user);
        if (penalty != null) {
            throw new Exception("이미 패널티가 존재합니다.");
        } else {
            penalty = Penalty.builder()
                    .user(user)
//                .appointment(appointment)
                    .penaltyType(penaltyType)
                    .content(content)
                    .fine(fine)
                    .build();
        }


        // 패널티 타입에 따라 초기화
        if (penaltyType == PenaltyType.FINE) {
            penalty.toBuilder().content(null);
        } else {
            penalty.toBuilder().fine(0);
        }

        // 패널티 저장
        penaltyRepository.save(penalty);

        // 약속에 생성한 패널티 부여 후 저장
        appointment.setPenalty(penalty);
        appointmentRepository.save(appointment);
    }

    public PenaltyDTO getUserapptPenalty(long uaid) {
        UserAppt userAppts = userApptRepository.findUserApptById(uaid);
        User user = userRepository.findById(userAppts.getUser().getId()).orElseThrow();
        Appointment appointment = appointmentRepository.findAppointmentByUserApptsId(userAppts.getId());

        log.info("userAppts : {}", userAppts.getId());
        log.info("user : {}", user.getId());
        log.info("appointment : {}", appointment.getId());
        log.info("패널티 조회 = {}", penaltyRepository.findPenaltyByUser(user).getContent());

        Penalty penalty = penaltyRepository.findPenaltyByUser(user);
        return PenaltyDTO.toDTO(penalty);
    }

    public List<PenaltyDTO> getAllPenaltyies(HttpServletRequest httpServletRequest) {

        String token = jwtUtil.extractTokenFromHeader(httpServletRequest);
        log.info("appointment token {}", token);
        String userIdString = jwtUtil.getUserId(token);
        log.info("userIdString {}", userIdString);
        Long userId = Long.parseLong(userIdString);        //현재 사용자
        log.info("userid {}", userId);

        List<Penalty> penalties = penaltyRepository.findAllByUserId(userId);
        return penalties.stream()
                .map(PenaltyDTO::toDTO)  // Penalty를 PenaltyDTO로 변환
                .collect(Collectors.toList());
    }

    public List<ReceivedPenaltyDTO> getAllReceivedPenalties(HttpServletRequest httpServletRequest) {

        String token = jwtUtil.extractTokenFromHeader(httpServletRequest);
        log.info("appointment token {}", token);
        String userIdString = jwtUtil.getUserId(token);
        log.info("userIdString {}", userIdString);
        Long userId = Long.parseLong(userIdString);        //현재 사용자
        log.info("userid {}", userId);

        List<ReceivedPenalty> receivedPenalties = receivedPenaltyRepository.findByUserId(userId);
        return receivedPenalties.stream()
                .map(ReceivedPenaltyDTO::toDTO)
                .collect(Collectors.toList());
    }

    public void createReceivedPenalty(long uaid) {
        // uaid로 약속 조회 -> apptid로 패널티 조회 -> 받은 패널티 저장
        UserAppt userAppt = userApptRepository.findUserApptById(uaid);
        Appointment appointment = appointmentRepository.findAppointmentByUserApptsId(uaid);
        Penalty penalty = penaltyRepository.findByAppointmentId(appointment.getId());

        log.info("user appt : {}", userAppt.getId());
        log.info("appointment : {}", appointment.getPlace());
        // 지각인 유저 가져오기
        User user = userRepository.findById(userAppt.getUser().getId()).orElseThrow();

        log.info("저장하려는 패널티 : {}", penalty.getContent());
        ReceivedPenalty receivedPenalty = ReceivedPenalty.builder()
                .penalty(penalty)
                .user(user)
//                .resTime() // 추가 필요
                .build();

        receivedPenaltyRepository.save(receivedPenalty);
    }
}
