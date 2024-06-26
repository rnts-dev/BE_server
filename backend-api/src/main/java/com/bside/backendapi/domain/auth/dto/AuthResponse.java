package com.bside.backendapi.domain.auth.dto;

import com.bside.backendapi.global.jwt.vo.AccessToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse {

    private AccessToken accessToken;
    private boolean result;

    public static AuthResponse from(final AccessToken accessToken, final boolean result) {
        return new AuthResponse(accessToken, result);
    }
}
