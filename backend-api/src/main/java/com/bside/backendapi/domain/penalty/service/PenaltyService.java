package com.bside.backendapi.domain.penalty.service;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.penalty.entity.PenaltyType;
import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import com.bside.backendapi.domain.penalty.repository.PenaltyRepository;
import com.bside.backendapi.domain.penalty.repository.ReceivedPenaltyRepository;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.user.repository.UserRepository;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import com.bside.backendapi.domain.userappt.repository.UserApptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {

    private final UserRepository userRepository;
    private final UserApptRepository userApptRepository;
    private final AppointmentRepository appointmentRepository;
    private final PenaltyRepository penaltyRepository;
    private final ReceivedPenaltyRepository receivedPenaltyRepository;

    public void createPenalty(long uaid, PenaltyType penaltyType, String content, int fine) {
        UserAppt userAppts = userApptRepository.findUserApptById(uaid);
        User user = userRepository.findById(userAppts.getUser().getId()).orElseThrow();
        Appointment appointment = appointmentRepository.findAppointmentByUserApptsId(userAppts.getId());

        Penalty penalty = Penalty.builder()
                .user(user)
                .appointment(appointment)
                .penaltyType(penaltyType)
                .content(content)
                .fine(fine)
                .build();

        // 패널티 타입에 따라 초기화
        if (penaltyType == PenaltyType.FINE) {
            penalty.toBuilder().content(null);
        } else {
            penalty.toBuilder().fine(0);
        }

        // 패널티 저장
        penaltyRepository.save(penalty);

        // 약속에 생성한 패널티 부여 후 저장
        appointment.toBuilder().penalty(penalty).build();
        appointmentRepository.save(appointment);
    }

    public Penalty getUserapptPenalty(long uaid) {
        UserAppt userAppts = userApptRepository.findUserApptById(uaid);
        User user = userRepository.findById(userAppts.getUser().getId()).orElseThrow();;
        Appointment appointment = appointmentRepository.findAppointmentByUserApptsId(userAppts.getId());

        return penaltyRepository.findPenaltyByUserIdAndAppointmentId(user.getId(), appointment.getId());
    }

    public List<Penalty> getAllPenaltyies(long userId) {
        return penaltyRepository.findAllByUserId(userId);
    }

    public List<ReceivedPenalty> getAllReceivedPenalties(long userId) {
        return receivedPenaltyRepository.findByUserId(userId);
    }
}
