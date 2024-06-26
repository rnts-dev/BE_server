package com.bside.backendapi.domain.member.application;

import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.dto.MemberDetailsResponse;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberDetailsResponse getDetailBy(final CustomUserDetails userDetails) {

        MemberDetailsResponse memberDetailsResponse = memberRepository.findById(userDetails.getId())
                .map(MemberDetailsResponse::of)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        return saveCache(memberDetailsResponse);
    }

    public MemberDetailsResponse saveCache(final MemberDetailsResponse member) {
        return member;
    }
}
