package com.bside.backendapi.global.security.principal;

import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.Password;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginId loginId = LoginId.from(authentication.getName());
        Password password = Password.from(authentication.getCredentials().toString());

        CustomOAuth2User principal = (CustomOAuth2User) customUserDetailsService.loadUserByUsername(loginId.loginId());

        log.info("CustomAuthenticationProvider ----- ID : {}", loginId.loginId());
        log.info("CustomAuthenticationProvider ----- PW : {}", authentication.getCredentials());

        return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
