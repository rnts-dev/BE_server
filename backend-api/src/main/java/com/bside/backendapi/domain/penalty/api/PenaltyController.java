package com.bside.backendapi.domain.penalty.api;

import com.bside.backendapi.domain.penalty.application.PenaltyService;
import com.bside.backendapi.domain.penalty.dto.request.PenaltyCreateRequest;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyGetResponse;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyResponse;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
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

    //패널티 생성 (1등이 등록)
    @Operation(summary = "패널티 생성", description = "1등일 경우 패널티 생성 -> 지각x: 패널티 타입 선택 , 지각: 바로 반성문으로 생성 (content는 null)")
    @PostMapping("penalty/{appointmentId}")
    public ResponseEntity<PenaltyResponse> createPenalty(@RequestBody PenaltyCreateRequest request, @PathVariable Long appointmentId){

        //현재 로그인된 사용자
        Long memberId = this.getPrincipal().getId();

        //request를 패널티 객체로 만들어서  memberid 랑 appointmentId 와 서비스로 전송
        Long penaltyId = penaltyService.create(request.toEntity(), memberId, appointmentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PenaltyResponse(penaltyId, true));
    }


    //약속으로 패널티 조회
    @Operation(summary = "appointmentId로 패널티 조회", description = "1등 아닐경우 패널티 조회 -> 지각x: 조회해서 패널티 보여주기만, 지각: 패널티 보여주고 받는 패널티 등록")
    @GetMapping("penalty/{appointmentId}")
    public ResponseEntity<PenaltyGetResponse> findPenaltyByAppointmentId(@PathVariable Long appointmentId){

        //findByAppointment 메소드 동작
        PenaltyGetResponse penaltyGetResponse = penaltyService.findByAppointment(appointmentId);

        return ResponseEntity.ok(penaltyGetResponse);
    }


    //패널티 등록 (내가 받는 패널티 등록)
    @Operation(summary = "내가 받는 패널티 등록", description = "지각 했을 때 receivedPenalty에 패널티 등록")
    @PostMapping("penalty/received/{penaltyId}")
    public ResponseEntity<PenaltyResponse> addReceivedMember(@PathVariable Long penaltyId){

        //현재 로그인 사용자 받아오기
        Long memberId = this.getPrincipal().getId();

        //addReceiver 메소드 동작
        PenaltyResponse penaltyResponse = penaltyService.addReceiver(penaltyId, memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(penaltyResponse);

    }


    //내가 생성한 패널티 전체 조회
    @Operation(summary = "내가 생성한 패널티 전체 조회", description = "1등 아닌경우 패널티 조회")
    @GetMapping("penalties/my-created")
    public ResponseEntity<List<PenaltyGetResponse>> getMyCreatedPenalties(){

        //현재 로그인 사용자 받아오기
        Long memberId = this.getPrincipal().getId();

        //memberid 파라미터로, MyCreatedPenalties 메서드 동작
        List<PenaltyGetResponse> penaltyGetResponses = penaltyService.MyCreatedPenalties(memberId);

        return ResponseEntity.ok(penaltyGetResponses);
    }


    // 내가 받은(등록된) 패널티 조회
    @Operation(summary = "받은 패널티 전체 조회", description = ".")
    @GetMapping("penalies")
    public ResponseEntity<List<PenaltyGetResponse>> getMyPenaltiest(){

        //현재 로그인 사용자 받아오기
        Long memberId = this.getPrincipal().getId();

        //memberid 파라미터로, myPenalties 메서드 동작
        List<PenaltyGetResponse> penaltyGetResponses = penaltyService.myPenalties(memberId);

        return ResponseEntity.ok(penaltyGetResponses);
    }


    //현재 로그인한 사용자 불러오는 메서드
    private CustomOAuth2User getPrincipal() {
        return (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
