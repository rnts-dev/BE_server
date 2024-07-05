package com.bside.backendapi.domain.auth.application;

import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.Password;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.jwt.TokenProvider;
import com.bside.backendapi.global.jwt.dto.TokenDTO;
import com.bside.backendapi.global.jwt.error.TokenNotFoundException;
import com.bside.backendapi.global.jwt.vo.AccessToken;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder builder;
    private final TokenProvider tokenProvider;

    public TokenDTO login(final LoginId loginId, final Password password) {
        // 1. Login - ID, Password 기반으로 Authentication 객체 생성
        final String userPassword = password.password();

        CustomOAuth2User customUserDetails = memberRepository.findUserDetailsByLoginId(loginId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(customUserDetails, userPassword);

        // 2. 실제 인증 (사용자 비밀번호 체크) : authenticate() 가 실행될 때, CustomOAuth2UserService 에서 만든 loadUserByUsername() 실행
        Authentication authentication = builder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 인증 정보를 기반으로 JWT 생성
        return tokenProvider.createToken(customUserDetails.getId(), authentication);
    }

    public AccessToken reissue(final String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new TokenNotFoundException(ErrorCode.TOKEN_NOT_FOUND);
        }

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(principal.getId(), authentication).getAccessToken();
    }
}
