package com.bside.backendapi.domain.penalty.application;
import com.bside.backendapi.domain.member.application.MemberService;
import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.dto.MemberResponse;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.domain.member.util.GivenMember;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
import com.bside.backendapi.domain.penalty.dto.request.PenaltyCreateRequest;
import com.bside.backendapi.domain.penalty.dto.request.TempPenaltyCreateRequest;
import com.bside.backendapi.global.error.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PenaltyServiceTest {

    @Autowired
    PenaltyService penaltyService;

    @Autowired
    PenaltyRepository penaltyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    static Member testmember;
    static Long penaltyId;
    @BeforeEach
    void init(){
        MemberResponse joinResponse = memberService.join(GivenMember.toEntity());

        testmember = memberRepository.findById(joinResponse.getMemberId()).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("패널티 생성")
    @Transactional(readOnly = true)
    void createPenalty(){
        TempPenaltyCreateRequest request = getTempPenaltyCreateRequest(testmember.getId());
        Long penaltyId = penaltyService.create(request.toEntity(), testmember.getId(), null);


    }

    private TempPenaltyCreateRequest getTempPenaltyCreateRequest(Long ceatorId){
        return new TempPenaltyCreateRequest(
                PenaltyType.FINE,
                "테스트 페널티",
                10000,
                ceatorId
        );
    }



}
