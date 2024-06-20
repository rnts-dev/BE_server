package com.bside.backendapi.domain.penalty.api;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.penalty.application.PenaltyService;
import com.bside.backendapi.domain.penalty.dto.request.PenaltyCreateRequest;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyGetResponse;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PenaltyController {

    private final PenaltyService penaltyService;

    //패널티 생성 (1등이 등록) 일단 받은 사람은 null으로
    @PostMapping("penalty/{appointmentId}")
    public ResponseEntity<PenaltyResponse> createPenalty(@RequestBody PenaltyCreateRequest request,
                                                            @PathVariable Long appointmentId){
        Member temptMember;
        Long tempMemberId = 1L;
        Long penaltyId = penaltyService.create(request.toEntity(), tempMemberId, appointmentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PenaltyResponse(penaltyId, true));
    }

    //패널티 조회
    @GetMapping("penalty/{appointmentId}")
    public ResponseEntity<PenaltyGetResponse> findPenaltyByAppointmentId(@PathVariable Long appointmentId){
        Long penaltyId = penaltyService.findByAppointment(appointmentId);
        if (penaltyId == null){
            return ResponseEntity.ok(new PenaltyGetResponse(null, false));
        }
        return ResponseEntity.ok(new PenaltyGetResponse(penaltyId, true));
    }


    //패널티 등록
    @GetMapping("penalty/receiveed/{penaltyId}")
    public ResponseEntity<PenaltyResponse> addReceivedMember(@PathVariable Long penaltyId){

        Long tempMemberId = 1L; //임시 현재 사용자

        Long updatedPenaltyId = penaltyService.addReceiver(penaltyId, tempMemberId);

        //임시
        return ResponseEntity.status(HttpStatus.CREATED).body(new PenaltyResponse(updatedPenaltyId, true));
    }



}
