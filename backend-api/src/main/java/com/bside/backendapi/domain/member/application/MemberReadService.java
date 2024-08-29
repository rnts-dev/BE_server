package com.bside.backendapi.domain.member.application;

import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.dto.MemberDetailsResponse;
import com.bside.backendapi.domain.member.exception.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberDetailsResponse getDetailBy(final CustomOAuth2User userDetails) {

        MemberDetailsResponse memberDetailsResponse = memberRepository.findById(userDetails.getId())
                .map(MemberDetailsResponse::of)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return saveCache(memberDetailsResponse);
    }

    public MemberDetailsResponse saveCache(final MemberDetailsResponse member) {
        return member;
    }
}
