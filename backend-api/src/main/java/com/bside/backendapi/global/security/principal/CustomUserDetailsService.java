package com.bside.backendapi.global.security.principal;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    public static final MemberNotFoundException NOT_FOUND_EXCEPTION = new MemberNotFoundException(ErrorCode.USER_NOT_FOUND);

    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByIdWithDetails(Long.valueOf(memberId))
                .orElseThrow(() -> NOT_FOUND_EXCEPTION);
    }
}
