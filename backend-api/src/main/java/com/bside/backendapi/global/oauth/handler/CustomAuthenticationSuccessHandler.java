package com.bside.backendapi.global.oauth.handler;

import com.bside.backendapi.global.jwt.application.TokenProvider;
import com.bside.backendapi.global.jwt.dto.TokenDTO;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Value("${jwt.header}")
    private String HEADER;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 일반 로그인, 소셜 로그인 유저 모두 처리
        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();

        // JWT 생성
        TokenDTO token = tokenProvider.createToken(user.getLoginId(), authentication);

        log.info("success handler : {}", token);

        // Response Header로 전달
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HEADER, token.getAccessToken().accessToken());

        // Http 응답에 JSON 데이터 작성
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(HEADER, token.getAccessToken().accessToken());
        new ObjectMapper().writeValue(response.getWriter(), responseMap);
    }
}