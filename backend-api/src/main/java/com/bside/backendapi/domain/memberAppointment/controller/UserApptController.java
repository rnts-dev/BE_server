package com.bside.backendapi.domain.memberAppointment.controller;

import com.bside.backendapi.domain.memberAppointment.dto.response.UserApptResponse;
import com.bside.backendapi.domain.memberAppointment.dto.response.CheckinResponse;
import com.bside.backendapi.domain.memberAppointment.service.UserApptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    private final UserApptService userApptService;

    @PostMapping("/{appointmentId}")
    @Operation(summary = "초대", description = "초대메서드 appointment id값 입력")
    public ResponseEntity<Objects> joinAppointment(@PathVariable Long appointmentId, HttpServletRequest httpRequest){
        //초대 받은 appointment랑 현재 사용자로 생성
        userApptService.createUserAppt(appointmentId, httpRequest);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/myappt")
    @Operation(summary = "내 약속 모두 조회", description = ".")
    public ResponseEntity<List<UserApptResponse>> getAllMyUserAppt(HttpServletRequest httpRequest){

        return ResponseEntity.ok(userApptService.findAllMyUserAppt(httpRequest));
    }

    @GetMapping("/myappt/past")
    @Operation(summary = "내 지난 약속 모두 조회", description = ".")
    public ResponseEntity<List<UserApptResponse>> getPastMyUserAppt(HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(userApptService.findAllMyUserApptPast(httpServletRequest));
    }

    @GetMapping("/myappt/future")
    @Operation(summary = "내 남은 약속 모두 조회", description = ".")
    public ResponseEntity<List<UserApptResponse>> getFutureMyUserAppt(HttpServletRequest httpRequest){

        return ResponseEntity.ok(userApptService.findAllMyUserApptFuture(httpRequest));
    }



    @PostMapping("checkin/{id}")
    @Operation(
            summary = "체크인",
            description = """
    resTime: 편차시간 빠르면 -로 표기 (분)  / /  checkType: success(정상접근 지각 or 안지각) - disable(30분 이전 아직 비활성화) - already(이미 체크인 완료),
    / /   isLate: 지각여부,
    / /   isFirst: 1등여부
    """
    )
    public ResponseEntity<CheckinResponse> checkIn(@PathVariable Long id, HttpServletRequest httpRequest){

        return ResponseEntity.ok(userApptService.checkIn(id, httpRequest));
    }

}
