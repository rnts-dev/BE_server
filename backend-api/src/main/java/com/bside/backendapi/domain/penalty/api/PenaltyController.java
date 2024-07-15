package com.bside.backendapi.domain.penalty.api;

import com.bside.backendapi.domain.penalty.application.PenaltyService;
import com.bside.backendapi.domain.penalty.dto.request.PenaltyCreateRequest;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyGetResponse;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyResponse;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PenaltyController {

    private final PenaltyService penaltyService;

    //패널티 생성 (1등이 등록) 일단 받은 사람은 null으로
    @PostMapping("penalty/{appointmentId}")
    public ResponseEntity<PenaltyResponse> createPenalty(@RequestBody PenaltyCreateRequest request,
                                                            @PathVariable Long appointmentId){

        Long memberId = this.getPrincipal().getId();
        Long penaltyId = penaltyService.create(request.toEntity(), memberId, appointmentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PenaltyResponse(penaltyId, true));
    }

    //패널티 조회
    @GetMapping("penalty/{appointmentId}")
    public ResponseEntity<PenaltyGetResponse> findPenaltyByAppointmentId(@PathVariable Long appointmentId){
        PenaltyGetResponse penaltyGetResponse = penaltyService.findByAppointment(appointmentId);

        return ResponseEntity.ok(penaltyGetResponse);
    }


    //패널티 등록 (내가 받는 패널티 등록)
    @PostMapping("penalty/receiveed/{penaltyId}")
    public ResponseEntity<PenaltyResponse> addReceivedMember(@PathVariable Long penaltyId){

        Long memberId = this.getPrincipal().getId();

        Long updatedPenaltyId = penaltyService.addReceiver(penaltyId, memberId);

        return ResponseEntity.ok(new PenaltyResponse(penaltyId, true));

    }


    //내가 생성한 패널티 조회
    @GetMapping("penalties/my-created")
    public ResponseEntity<List<PenaltyGetResponse>> getMyCreatedPenalties(){

        Long memberId = this.getPrincipal().getId();

        List<PenaltyGetResponse> penaltyGetResponses = penaltyService.MyCreatedPenalties(memberId);

        return ResponseEntity.ok(penaltyGetResponses);
    }


    // 내가 받은 패널티 조회
    @GetMapping("penalies")
    public ResponseEntity<List<PenaltyGetResponse>> getMyPenaltiest(){

        Long memberId = this.getPrincipal().getId();

        List<PenaltyGetResponse> penaltyGetResponses = penaltyService.myPenalties(memberId);

        return ResponseEntity.ok(penaltyGetResponses);
    }

    private CustomOAuth2User getPrincipal() {
        return (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
