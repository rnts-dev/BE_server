package com.bside.backendapi.global.security.principal;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    public static final MemberNotFoundException NOT_FOUND_EXCEPTION = new MemberNotFoundException(ErrorCode.USER_NOT_FOUND);

    private final MemberRepository memberRepository;

    // 메서드 종료시 @AuthenticationPrincipal 이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(final String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(memberId)).orElseThrow(() -> NOT_FOUND_EXCEPTION);
        if (member != null)
            return new CustomOAuth2User(member);
        return null;
//        return memberRepository.findByIdWithDetails(Long.valueOf(memberId)).orElseThrow(() -> NOT_FOUND_EXCEPTION);
    }
}
