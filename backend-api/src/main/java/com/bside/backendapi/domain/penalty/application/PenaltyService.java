package com.bside.backendapi.domain.penalty.application;


import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.appointment.exception.AppointmentNotFound;
import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import com.bside.backendapi.domain.penalty.domain.persist.ReceivedPenalty;
import com.bside.backendapi.domain.penalty.domain.persist.ReceivedPenaltyRepository;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyGetResponse;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyResponse;
import com.bside.backendapi.domain.penalty.error.PenaltyAlreadyExistsException;
import com.bside.backendapi.domain.penalty.error.PenaltyNotFoundExepception;
import com.bside.backendapi.domain.penalty.error.ReceivedPenaltySaveException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;
    private final AppointmentRepository appointmentRepository;
    private final ReceivedPenaltyRepository receivedPenaltyRepository;
    private final MemberRepository memberRepository;

    //패널티 생성 서비스
    @Transactional
    public Long create(final Penalty penalty, final Long memberId, final  Long appointmentId){

        //패널티 저장 할 객체 생성
        Penalty savedPenalty;

        //penaltyType REFLECTION 아니면 패널티 사용자 penaltyCreatorId 추가 후 저장
        if (!"REFLECTION".equals(penalty.getPenaltyType())) {
            savedPenalty = penaltyRepository.save(penalty.addPenaltyCreatorId(memberId));
        }
        //penaltyType REFLECTION 일 때 그냥 penalty 저장
        else{
            savedPenalty = penaltyRepository.save(penalty);
        }

        //appointmentId로 appointment 가져오기
        Appointment updatedAppointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new AppointmentNotFound(ErrorCode.APPOINTMENT_NOT_FOUND)
        );

        //appointment에 의미 패널티 저장되어있으면 예외처리
        if (updatedAppointment.getPenaltyId() != null){
            throw new PenaltyAlreadyExistsException(ErrorCode.PENALTY_ALREADY_EXISTS);
        }

        //해당 appointment에 penaltyid 추가
        updatedAppointment.addPenalty(savedPenalty.getId());

        //appointment 변경사항 저장
        appointmentRepository.save(updatedAppointment);

        return savedPenalty.getId();
    }


    //약속으로 패널티 조회 서비스
    public PenaltyGetResponse findByAppointment(final Long appointmentId){

        //약속 조회
        Appointment findAppointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new AppointmentNotFound(ErrorCode.APPOINTMENT_NOT_FOUND)
        );

        //약속에서 패널티id 가져오기
        Long penaltyId = findAppointment.getPenaltyId();
        if (penaltyId == null){
            //약속에 등록된 패널티 없으면 null값 담아서 리턴
            return PenaltyGetResponse.empty();
        }

        //패널티id로 패널티 정보 가져오기
        Penalty getPenalty = penaltyRepository.findById(penaltyId).orElseThrow(
                () -> new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND)
        );

        //dto변환 후 response 담아서 리턴
        return PenaltyGetResponse.of(getPenalty);
    }


    //패널티 등록 서비스 (패널티 받는 사람 등록)
    public PenaltyResponse addReceiver(final Long penaltyId, final Long memberId){

        // penaltyId가 유효한지 확인 (연관관계 사용 안해서 직접 검사)
        Penalty penalty = penaltyRepository.findById(penaltyId)
                .orElseThrow(() -> new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND));

        // memberId가 유효한지 확인 (연관관계 사용 안해서 직접 검사)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        //receivedPenalty 객체 생성
        ReceivedPenalty receivedPenalty = ReceivedPenalty.builder()
                .penaltyId(penaltyId)
                .memberId(memberId)
                .build();

        //receivedPenalty 저장
        ReceivedPenalty savedReceivedPenalty = receivedPenaltyRepository.save(receivedPenalty);

        // 저장 실패 시 예외 던지기
        if (savedReceivedPenalty == null || !savedReceivedPenalty.getPenaltyId().equals(penaltyId)) {
            throw new ReceivedPenaltySaveException(ErrorCode.RECEIVED_PENALTY_SAVE_ERROR);
        }

        //dto변환 후 response 담아서 리턴
        return PenaltyResponse.of(savedReceivedPenalty.getPenaltyId(), true);
    }



    //내가 생성한 패널티 조회
    public List<PenaltyGetResponse> MyCreatedPenalties(final Long memberId){

        //penalty에서 memberid와 creatorid 일치하는 것들 리스트로 가져오기
        List<Penalty> penalties = penaltyRepository.findByPenaltyCreatorId(memberId);

        //받아온 penalties 예외처리
        if (penalties == null || penalties.isEmpty()){
            throw new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND);
        }

        //penalties dto값으로 변환하여 response로
        List<PenaltyGetResponse> penaltyResponses = penalties.stream()
                .map(PenaltyGetResponse::of)
                .collect(Collectors.toList());

        //response 리턴
        return penaltyResponses;
    }


    //내가 받은 패널티 조회
    public List<PenaltyGetResponse> myPenalties(final Long memberId){

        //memberid로 receivedPenalties(penaltyid + memberid) 가져오기
        List<ReceivedPenalty> receivedPenalties = receivedPenaltyRepository.findByMemberId(memberId);

        //받아온 receivedPenalties 예외처리
        if (receivedPenalties == null || receivedPenalties.isEmpty()){
            throw new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND);
        }

        //receivedPenalties에서 penaltyid 추출
        List<Long> penaltyIds = receivedPenalties.stream()
                .map(ReceivedPenalty::getPenaltyId)
                .collect(Collectors.toList());

        //추출한 penaltyid로 penalty정보 추출
        List<Penalty> penalties = penaltyRepository.findAllById(penaltyIds);

        //penalties 예외처리
        if (penalties.isEmpty()) {
            throw new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND);
        }

        //penaltyResponses에 담아서 리턴
        List<PenaltyGetResponse> penaltyGetResponses = penalties.stream()
                .map(PenaltyGetResponse::of)
                .collect(Collectors.toList());

        return penaltyGetResponses;
    }



}
