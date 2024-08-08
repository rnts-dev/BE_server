package com.bside.backendapi.global.jwt.application;

import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.jwt.dto.TokenDTO;
import com.bside.backendapi.global.jwt.error.TokenNotFoundException;
import com.bside.backendapi.global.jwt.vo.AccessToken;
import com.bside.backendapi.global.jwt.vo.RefreshToken;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import com.bside.backendapi.global.security.principal.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "role";
    private final String secretKey;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
    private final CustomUserDetailsService customUserDetailsService;

    private SecretKey key;

    public TokenProvider(@Value("${jwt.secretKey}") String secretKey,
                         @Value("${jwt.access.expiration}") Long accessTokenExpiration,
                         @Value("${jwt.refresh.expiration}") Long refreshTokenExpiration,
                         CustomUserDetailsService customUserDetailsService) {
        this.secretKey = secretKey;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDTO createToken(LoginId loginId, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = generateToken(loginId, authorities, accessTokenExpiration);
        String refreshToken = generateToken(loginId, authorities, refreshTokenExpiration);

        AccessToken newAccessToken = AccessToken.from(accessToken);
        RefreshToken newRefreshToken = RefreshToken.from(refreshToken, loginId);

        return TokenDTO.of(newAccessToken, newRefreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token).getPayload();

        List<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String loginId = String.valueOf(claims.get("id"));

        CustomOAuth2User principal = (CustomOAuth2User) customUserDetailsService.loadUserByUsername(loginId);

        return new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(this.key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
            throw new TokenNotFoundException(ErrorCode.TOKEN_NOT_FOUND);
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
            throw new TokenNotFoundException(ErrorCode.TOKEN_EXPIRED);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
            throw new TokenNotFoundException(ErrorCode.TOKEN_INVALID);
        }
    }

    public String generateToken(LoginId loginId, String authorities, Long expiration) {
        long expirationTime = (new Date()).getTime() + expiration;
        return Jwts.builder()
                .claim("id", loginId.loginId())
                .claim(AUTHORITIES_KEY, authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(expirationTime))
                .signWith(this.key, Jwts.SIG.HS512)
                .compact();
    }

    public String generateTokenForMail(Email mail) {
        long expirationTime = (new Date()).getTime() + 1000 * 60 * 15;
        return Jwts.builder()
                .claim("mail", mail.email())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(expirationTime))
                .signWith(this.key, Jwts.SIG.HS512)
                .compact();
    }

    public Claims getMailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token).getPayload();
    }

}
