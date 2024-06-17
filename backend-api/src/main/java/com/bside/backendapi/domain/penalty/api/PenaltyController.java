package com.bside.backendapi.domain.penalty.api;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.penalty.application.PenaltyService;
import com.bside.backendapi.domain.penalty.dto.request.PenaltyCreateRequest;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PenaltyController {

    private final PenaltyService penaltyService;

    //패널티 생성 (1등이 등록) 일단 받은 사람은 null으로
    @PostMapping("penalty")
    public ResponseEntity<PenaltyResponse> create(@RequestBody PenaltyCreateRequest request){
        Member temptMember;
        Long tempMemberId = 1L;
        Long penaltyId = penaltyService.create(request.toEntity(), tempMemberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PenaltyResponse(penaltyId, true));
    }


    
}
