package com.bside.backendapi.domain.penalty.controller;

import com.bside.backendapi.domain.penalty.dto.PenaltyRequest;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyResponse;
import com.bside.backendapi.domain.penalty.dto.response.UserApptPenaltyResponse;
import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.penalty.service.PenaltyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/penalty")
@Tag(name = "Penalty", description = "Penalty API Document / uaid = userAppointmentId")
public class PenaltyController {

    private final PenaltyService penaltyService;

    @Operation(summary = "패널티 생성", description = "패널티 생성 : 1등 도착한 사람")
    @PostMapping("/{uaid}")
    public ResponseEntity<Objects> createPenalty(
            @PathVariable("uaid") long uaid,
            @RequestBody PenaltyRequest penaltyRequest) {

        penaltyService.createPenalty(uaid, penaltyRequest.getPenaltyType(),
                penaltyRequest.getContent(), penaltyRequest.getFine());

        return ResponseEntity.ok().build();
    }

    //1등 아닌사람
    @Operation(summary = "해당 userappt 패널티 조회", description = "패널티 조회 : 1등 아닌사람")
    @GetMapping("/{uaid}")
    public ResponseEntity<Penalty> getUserapptPenalty(
            @PathVariable("uaid") long uaid) {
//            @RequestBody PenaltyRequest penaltyRequest) {
        return ResponseEntity.ok(penaltyService.getUserapptPenalty(uaid));
    }

    //내가 보낸 패널티
    @Operation(summary = "내가 보낸 패널티 조회", description = ".")
    @GetMapping("/{userid}")
    public ResponseEntity<List<Penalty>> getAllPenalties(@PathVariable("userid") long userId){
        return ResponseEntity.ok(penaltyService.getAllPenaltyies(userId));
    }

    //내일 좀 더 생각 머리 멈춤
    @Operation(summary = "내가 받은 패널티 조회", description = "수정 필요.")
    @GetMapping("received/{userid}")
    public ResponseEntity<List<UserApptPenaltyResponse>> getAllReceivedPenalties(){

        return ResponseEntity.ok().build();
    }

}
