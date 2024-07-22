package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.Password;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    @Valid
    @NotNull(message = "ID를 입력하세요.")
    private LoginId loginId;

    @Valid
    @NotNull(message = "Password를 입력하세요.")
    private Password password;

    public static LoginRequest of(final LoginId loginId, final Password password) {
        return new LoginRequest(loginId, password);
    }
}
