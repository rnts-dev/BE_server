package com.bside.backendapi.global.jwt.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessToken {

    @NotBlank(message = "토큰이 존재하지 않습니다.")
    private String accessToken;

    @JsonValue
    public String accessToken() {
        return accessToken;
    }

    public static AccessToken from(final String accessToken) {
        return new AccessToken(accessToken);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccessToken accessToken = (AccessToken) obj;
        return Objects.equals(accessToken(), accessToken.accessToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken());
    }
}
