package com.bside.backendapi.domain.auth.api;

import com.bside.backendapi.domain.auth.application.AuthService;
import com.bside.backendapi.domain.auth.dto.AuthResponse;
import com.bside.backendapi.domain.auth.dto.LoginRequest;
import com.bside.backendapi.global.jwt.dto.TokenDTO;
import com.bside.backendapi.global.jwt.vo.RefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/public/auth/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenDTO token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        RefreshToken refreshToken = token.getRefreshToken();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, getCookie(refreshToken).toString())
                .body(AuthResponse.from(token.getAccessToken(), true));
    }

    @DeleteMapping("/auth/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletResponse httpServletResponse) {
        Cookie myCookie = new Cookie("refreshToken", null);
        myCookie.setMaxAge(0);
        myCookie.setPath("/");
        httpServletResponse.addCookie(myCookie);

        return ResponseEntity.noContent().build();
    }

    private ResponseCookie getCookie(RefreshToken refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken.refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(18000)
                .build();
    }
}
