package com.bside.backendapi.global.jwt.vo;

import com.bside.backendapi.domain.member.vo.LoginId;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 14400) // timeToLive: 초단위
public class RefreshToken {
    // java.persistence.Id가 아닌 opg.springframework.data.annotation.Id를 import 해야한다.
    // Refresh Token은 Redis에 저장하기 때문에 JPA 의존성이 필요하지 않다.
    @Id @NotBlank(message = "토큰이 존재하지 않습니다.")
    private final String refreshToken;
    private final LoginId loginId;

    @JsonValue
    public String refreshToken() {
        return refreshToken;
    }

    public static RefreshToken from(final String refreshToken, final LoginId loginId) {
        return new RefreshToken(refreshToken, loginId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RefreshToken refreshToken = (RefreshToken) obj;
        return Objects.equals(refreshToken(), refreshToken.refreshToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken());
    }
}
