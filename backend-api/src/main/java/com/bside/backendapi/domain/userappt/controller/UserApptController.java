package com.bside.backendapi.domain.userappt.controller;

import com.bside.backendapi.domain.userappt.dto.request.CheckInRequest;
import com.bside.backendapi.domain.userappt.dto.request.JoinRequest;
import com.bside.backendapi.domain.userappt.dto.UserApptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/userappt")
public class UserApptController {

    @PostMapping("/")
    public ResponseEntity<Objects> joinAppointment(@RequestBody JoinRequest joinRequest){

        //create userappointment

        return ResponseEntity.ok().build();
    }


    @GetMapping("/myappt")
    public ResponseEntity<List<UserApptResponse>> getAllMyUserAppt(){

        return ResponseEntity.ok().build();
    }

    @GetMapping("/myappt/past")
    public ResponseEntity<List<UserApptResponse>> getPastMyUserAppt(){

        return ResponseEntity.ok().build();
    }

    @GetMapping("/myappt/future")
    public ResponseEntity<List<UserApptResponse>> getFutureMyUserAppt(){

        return ResponseEntity.ok().build();
    }


    @PostMapping("/{id}")
    public ResponseEntity<Objects> createPenalty(@PathVariable Long id){
        //일단 체크인 이후 메서드 (등수 정해진 후 호출) -> 1등만 이 메서드 호출
        return  ResponseEntity.ok().build();
    }



    @PostMapping("checkin")
    public ResponseEntity<Objects> checkIn(@PathVariable Long id, @RequestBody CheckInRequest checkInRequest){

        return ResponseEntity.ok().build();
    }

}
