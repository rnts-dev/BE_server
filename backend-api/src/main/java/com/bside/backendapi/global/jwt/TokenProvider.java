package com.bside.backendapi.global.jwt;

import com.bside.backendapi.global.jwt.dto.TokenDTO;
import com.bside.backendapi.global.jwt.vo.AccessToken;
import com.bside.backendapi.global.jwt.vo.RefreshToken;
import com.bside.backendapi.global.security.principal.CustomUserDetails;
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

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final CustomUserDetailsService customUserDetailsService;

    private SecretKey key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessToken-validity-in-seconds}") long accessTokenValidityInMilliseconds,
            @Value("${jwt.refreshToken-validity-in-seconds}") long refreshTokenValidityInMilliseconds,
            CustomUserDetailsService customUserDetailsService) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDTO createToken(Long memberId, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        log.info("authorities : {}", authorities);

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
                .claim("id", memberId.toString())
                .claim(AUTHORITIES_KEY, authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(now + accessTokenValidityInMilliseconds))
                .signWith(this.key)
                .compact();

        String refreshToken = Jwts.builder()
                .claim(AUTHORITIES_KEY, authorities)
                .claim("id", memberId.toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(now + refreshTokenValidityInMilliseconds))
                .signWith(this.key)
                .compact();

        AccessToken newAccessToken = AccessToken.from(accessToken);
        RefreshToken newRefreshToken = RefreshToken.from(refreshToken);

        return TokenDTO.of(newAccessToken, newRefreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(this.key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        List<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String id = String.valueOf(claims.get("id"));

        CustomUserDetails principal = customUserDetailsService.loadUserByUsername(id);

        return new UsernamePasswordAuthenticationToken(principal, "password", authorities);
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
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }
}
