package com.bside.backendapi.global.oauth.handler;

import com.bside.backendapi.global.jwt.application.TokenProvider;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.header}")
    private String HEADER;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();

        String accessToken = tokenProvider.createToken(user.getUsername(), authentication);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setHeader(HEADER, "Bearer " + accessToken);

        log.info("access token : {}", "Bearer " + accessToken);

        // 리디렉션 URL 구성
        String redirectUrl = UriComponentsBuilder.fromUriString("https://www.rnts.o-r.kr")
                .queryParam(HEADER, accessToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}