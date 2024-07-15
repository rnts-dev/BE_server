//package com.bside.backendapi.global.oauth.handler;
//
//import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
//import com.bside.backendapi.domain.member.domain.vo.Email;
//import com.bside.backendapi.domain.member.error.MemberNotFoundException;
//import com.bside.backendapi.global.error.exception.ErrorCode;
//import com.bside.backendapi.global.jwt.application.TokenProvider;
//import com.bside.backendapi.global.jwt.repository.RefreshTokenRepository;
//import com.bside.backendapi.global.jwt.vo.RefreshToken;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//
//import java.io.IOException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    // 일반 로그인 성공
//    // 1. Access Token 발급 후 Http 헤더에 담아 전송
//    // 2. Refresh Token 발급 후 Redis 저장
//
//    private final TokenProvider tokenProvider;
//    private final MemberRepository memberRepository;
//    private final RefreshTokenRepository refreshTokenRepository;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        String email = extractUsername(authentication); // 인증 정보에서 Username 추출 (CustomUserDetails - Email)
//        Long memberId = memberRepository.findUserDetailsByEmail(Email.from(email))
//                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND))
//                .getId();
//
//        String accessToken = tokenProvider.createAccessToken(memberId);
//        String refreshToken = tokenProvider.createRefreshToken();
//
//        // Redis에 Refresh Token 저장
//        RefreshToken saveRefreshToken = new RefreshToken(refreshToken, memberId);
//        refreshTokenRepository.save(saveRefreshToken);
//
//        tokenProvider.sendAccessToken(response, accessToken);
//        log.info("LoginSuccessHandler -------- access token: {}", accessToken);
//    }
//
//    private String extractUsername(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return userDetails.getUsername();
//    }
//}
