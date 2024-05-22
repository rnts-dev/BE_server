package com.bside.backendapi.domain.penalty.controller;

import com.bside.backendapi.domain.penalty.dto.PenaltyRequest;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/penalty")
public class PenaltyController {

    @PostMapping("/{uaid}")
    public ResponseEntity<Objects> createPenalty(@RequestBody PenaltyRequest penaltyRequest) {
        //1등만 등록
        return ResponseEntity.ok().build();
    }


    //1등 아닌사람
    @GetMapping("/{uaid}")
    public ResponseEntity<Objects> getUserapptPenalty(@RequestBody PenaltyRequest penaltyRequest) {

        return ResponseEntity.ok().build();
    }


    //내가 보낸 패널티
    @GetMapping("/{userid}")
    public ResponseEntity<List<PenaltyResponse>> getAllPenalty(){

        return ResponseEntity.ok().build();
    }
}
