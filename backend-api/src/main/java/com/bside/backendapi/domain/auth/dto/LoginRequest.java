package com.bside.backendapi.domain.auth.dto;

import com.bside.backendapi.domain.member.domain.vo.Email;
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
    @NotNull(message = "Email을 입력하세요.")
    private Email email;

    @Valid
    @NotNull(message = "Password를 입력하세요.")
    private Password password;

    public static LoginRequest of(final Email email, final Password password) {
        return new LoginRequest(email, password);
    }
}
