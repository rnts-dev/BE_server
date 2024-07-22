package com.bside.backendapi.global.security.principal;

import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    // 일반 로그인 서비스
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String loginId) throws UsernameNotFoundException {
        return memberRepository.findUserDetailsByLoginId(LoginId.from(loginId))
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
