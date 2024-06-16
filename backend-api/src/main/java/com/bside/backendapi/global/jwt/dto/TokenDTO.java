package com.bside.backendapi.global.jwt.dto;

import com.bside.backendapi.global.jwt.vo.AccessToken;
import com.bside.backendapi.global.jwt.vo.RefreshToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenDTO {

    @JsonProperty("access_token")
    private AccessToken accessToken;

    @JsonProperty("refesh_token")
    private RefreshToken refreshToken;

    public static TokenDTO of(final AccessToken accessToken, final RefreshToken refreshToken) {
        return new TokenDTO(accessToken, refreshToken);
    }
}
