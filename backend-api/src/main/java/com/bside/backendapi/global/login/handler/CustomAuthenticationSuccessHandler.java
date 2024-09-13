package com.bside.backendapi.global.login.handler;

import com.bside.backendapi.global.jwt.application.TokenProvider;
import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.header}")
    private String HEADER;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();

        String accessToken = tokenProvider.createToken(user.getUsername(), authentication);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setHeader(HEADER, "Bearer " + accessToken);

        log.info("access token : {}", "Bearer " + accessToken);
    }
}
