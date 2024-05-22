package com.bside.backendapi.domain.userappt.controller;

import com.bside.backendapi.domain.userappt.dto.request.CheckInRequest;
import com.bside.backendapi.domain.userappt.dto.request.JoinRequest;
import com.bside.backendapi.domain.userappt.dto.response.UserApptResponse;
import com.bside.backendapi.domain.userappt.dto.response.CheckinResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/userappt")
@Tag(name = "UserAppointment", description = "UserAppointment API Document")
public class UserApptController {

    @PostMapping("/")
    @Operation(summary = "초대", description = "초대메서드")
    public ResponseEntity<Objects> joinAppointment(@RequestBody JoinRequest joinRequest){

        //create userappointment

        return ResponseEntity.ok().build();
    }


    @GetMapping("/myappt")
    @Operation(summary = "내 약속 모두 조회", description = ".")
    public ResponseEntity<List<UserApptResponse>> getAllMyUserAppt(){

        return ResponseEntity.ok().build();
    }

    @GetMapping("/myappt/past")
    @Operation(summary = "내 지난 약속 모두 조회", description = ".")
    public ResponseEntity<List<UserApptResponse>> getPastMyUserAppt(){

        return ResponseEntity.ok().build();
    }

    @GetMapping("/myappt/future")
    @Operation(summary = "내 남은 약속 모두 조회", description = ".")
    public ResponseEntity<List<UserApptResponse>> getFutureMyUserAppt(){

        return ResponseEntity.ok().build();
    }



    @PostMapping("checkin")
    @Operation(summary = "체크인", description = "지각여부, 도착순서, 오차시간 리턴")
    public ResponseEntity<CheckinResponse> checkIn(@PathVariable Long id, @RequestBody CheckInRequest checkInRequest){

        return ResponseEntity.ok().build();
    }

}
