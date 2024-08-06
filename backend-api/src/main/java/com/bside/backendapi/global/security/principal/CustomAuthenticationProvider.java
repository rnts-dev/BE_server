package com.bside.backendapi.global.security.principal;

import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.Password;
import com.bside.backendapi.domain.member.error.MismatchPasswordException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;

    // 순환참조 발생 -> 생성자로 따로 주입
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginId loginId = LoginId.from(authentication.getName());
        Password password = Password.from(authentication.getCredentials().toString());

        CustomOAuth2User principal = (CustomOAuth2User) customUserDetailsService.loadUserByUsername(loginId.loginId());

        if (!passwordEncoder().matches(password.password(), principal.getPassword())) {
            throw new AuthenticationServiceException("비밀번호가 일치하지 않습니다.");
//            throw new MismatchPasswordException(ErrorCode.MISMATCH_PASSWORD);
        }
        return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
