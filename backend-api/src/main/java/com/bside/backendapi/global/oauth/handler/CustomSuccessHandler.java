package com.bside.backendapi.global.oauth.handler;

import com.bside.backendapi.global.jwt.TokenProvider;
import com.bside.backendapi.global.jwt.dto.TokenDTO;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    // OAuth2 로그인이 성공하면 실행될 성공 핸들러 -> JWT 발급
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //OAuth2User
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        TokenDTO token = tokenProvider.createToken(oAuth2User.getId(), authentication);

        log.info("AccessToken : {}", token);

        response.addCookie(createCookie(token.getAccessToken().accessToken()));
        response.sendRedirect("http://localhost:8080"); // 로그인 성공시 프론트에 알려줄 redirect 경로
        response.setStatus(HttpStatus.OK.value());
    }

    private Cookie createCookie(String value) {
        Cookie cookie = new Cookie("Authorization", value);
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
