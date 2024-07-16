package com.bside.backendapi.global.jwt.filter;

import com.bside.backendapi.global.jwt.application.TokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // "/login"으로 들어오는 요청은 Filter 작동 X
//        String NO_CHECK_URL = "/api/v1/public/auth/login";
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//
//        if (servletRequest.getRequestURI().equals(NO_CHECK_URL)) {
//            chain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
//        }
//
//        String jwt = resolveToken(servletRequest);
//        String requestURI = servletRequest.getRequestURI();
//
//        // 무조건 요청이 여길 거쳐가므로 예외처리는 생략
//        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//            Authentication authentication = tokenProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        else {
//            log.debug("유효한 JWT 토큰이 없습니다. uri: {}", requestURI);
//        }
//        chain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String NO_CHECK_URL = "/api/v1/public/auth/login";

        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
        }

        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        // 무조건 요청이 여길 거쳐가므로 예외처리는 생략
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            log.debug("유효한 JWT 토큰이 없습니다. uri: {}", requestURI);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
