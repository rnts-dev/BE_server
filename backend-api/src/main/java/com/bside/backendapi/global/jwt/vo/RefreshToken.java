package com.bside.backendapi.global.jwt.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @NotBlank(message = "토큰이 존재하지 않습니다.")
    private String refreshToken;

    @JsonValue
    public String refreshToken() {
        return refreshToken;
    }

    public static RefreshToken from(final String refreshToken) {
        return new RefreshToken(refreshToken);
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
