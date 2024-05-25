package com.bside.backendapi.domain.penalty.controller;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.penalty.dto.PenaltyDTO;
import com.bside.backendapi.domain.penalty.dto.PenaltyRequest;
import com.bside.backendapi.domain.penalty.dto.ReceivedPenaltyDTO;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyResponse;
import com.bside.backendapi.domain.penalty.dto.response.UserApptPenaltyResponse;
import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import com.bside.backendapi.domain.penalty.service.PenaltyService;
import com.bside.backendapi.global.jwt.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/penalty")
@Tag(name = "Penalty", description = "Penalty API Document / uaid = userAppointmentId")
public class PenaltyController {

    private final PenaltyService penaltyService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "패널티 생성", description = "패널티 생성 : 1등 도착한 사람")
    @PostMapping("/{uaid}")
    public ResponseEntity<Objects> createPenalty(
            @PathVariable("uaid") long uaid,
            @RequestBody PenaltyRequest penaltyRequest) {

        penaltyService.createPenalty(uaid, penaltyRequest.getPenaltyType(),
                penaltyRequest.getContent(), penaltyRequest.getFine());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "해당 userappt 패널티 조회", description = "패널티 조회 : 1등 아닌사람")
    @GetMapping("/{uaid}")
    public ResponseEntity<PenaltyDTO> getUserapptPenalty(
            @PathVariable("uaid") long uaid) {
//            @RequestBody PenaltyRequest penaltyRequest) {
        return ResponseEntity.ok(penaltyService.getUserapptPenalty(uaid));
    }

    @Operation(summary = "내가 보낸 패널티 조회", description = ".")
    @GetMapping("/my")
    public ResponseEntity<List<PenaltyDTO>> getAllPenalties(HttpServletRequest httpRequest){

        return ResponseEntity.ok(penaltyService.getAllPenaltyies(httpRequest));
    }

    @Operation(summary = "내가 받은 패널티 조회", description = ".")
    @GetMapping("/received")
    public ResponseEntity<List<ReceivedPenaltyDTO>> getAllReceivedPenalties(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(penaltyService.getAllReceivedPenalties(httpServletRequest));
    }

    @Operation(summary = "내가 받은 패널티 등록", description = ".")
    @PostMapping("/createReceivedPenalty/{uaid}")
    public ResponseEntity<Penalty> createReceivedPenalty(@PathVariable("uaid") long uaid) {
        penaltyService.createReceivedPenalty(uaid);
        return ResponseEntity.ok().build();
    }

}
