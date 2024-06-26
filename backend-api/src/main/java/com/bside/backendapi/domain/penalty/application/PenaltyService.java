package com.bside.backendapi.domain.penalty.application;


import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.appointment.error.AppointmentNotFound;
import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import com.bside.backendapi.domain.penalty.domain.persist.ReceivedPenalty;
import com.bside.backendapi.domain.penalty.domain.persist.ReceivedPenaltyRepository;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyGetResponse;
import com.bside.backendapi.domain.penalty.error.PenaltyNotFoundExepception;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;
    private final AppointmentRepository appointmentRepository;
    private final ReceivedPenaltyRepository receivedPenaltyRepository;

    //패널티 생성 서비스
    public Long create(final Penalty penalty, final Long memberId, final  Long appointmentId){

        //패널티 사용자 추가 후 저장
        Penalty savedPenalty = penaltyRepository.save(penalty.addPenaltyCreatorId(memberId));//set쓰지말고

        //해당 appointment에 penaltyid 추가
        Appointment updatedAppointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new AppointmentNotFound(ErrorCode.APPOINTMENT_NOT_FOUND)
        );
        updatedAppointment.addPenalty(savedPenalty.getId());
        appointmentRepository.save(updatedAppointment);

        return savedPenalty.getId();
    }


    //약속에서 패널티 조회
    public PenaltyGetResponse findByAppointment(final Long appointmentId){

        //이거랑 penaltyRepository에서 appointment로 penalty찾는거 중 어떤거? -> 연관간계 안써서 안되나?
        Appointment findAppointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new AppointmentNotFound(ErrorCode.APPOINTMENT_NOT_FOUND)
        );
        //조회성공 여부
        Long penaltyId = findAppointment.getPenaltyId();
        if (penaltyId == null){
            return PenaltyGetResponse.empty();
        }

        Penalty getPenalty = penaltyRepository.findById(penaltyId).orElseThrow(
                () -> new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND)
        );

        return PenaltyGetResponse.of(getPenalty);
    }


    //패널티 등록 서비스 (패널티 받는 사람 등록)
    public Long addReceiver(final Long penaltyId, final Long memberId){

        ReceivedPenalty receivedPenalty = ReceivedPenalty.builder()
                .penaltyId(penaltyId)
                .memberId(memberId)
                .build();

        receivedPenaltyRepository.save(receivedPenalty);

        return penaltyId;
    }



    //내가 생성한 패널티 조회
    public List<PenaltyGetResponse> MyCreatedPenalties(final Long memberId){

        List<Penalty> penalties = penaltyRepository.findByPenaltyCreatorId(memberId);

        if (penalties == null || penalties.isEmpty()){
            throw new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND);
        }
        List<PenaltyGetResponse> penaltyResponses = penalties.stream()
                .map(PenaltyGetResponse::of)
                .collect(Collectors.toList());

        return penaltyResponses;
    }

    //내가 받은 패널티 조회
    public List<PenaltyGetResponse> myPenalties(final Long memberId){
        List<Penalty> penalties = receivedPenaltyRepository.findByMemberId(memberId);
        if (penalties == null || penalties.isEmpty()){
            throw new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND);
        }
        List<PenaltyGetResponse> penaltyResponses = penalties.stream()
                .map(PenaltyGetResponse::of)
                .collect(Collectors.toList());

        return penaltyResponses;
    }

}
