package com.bside.backendapi.global.security.principal;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.repository.MemberRepository;
import com.bside.backendapi.domain.member.vo.LoginId;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(LoginId.from(loginId))
                .orElseThrow(() -> new UsernameNotFoundException("loadUserByUsername - 사용자를 찾을 수 없습니다."));
        return new CustomOAuth2User(member);
    }
}
