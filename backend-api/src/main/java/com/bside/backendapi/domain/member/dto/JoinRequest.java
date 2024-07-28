package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinRequest {

    @Valid
    @NotNull(message = "아이디는 필수입니다.")
    private LoginId loginId;

    @Valid
    @NotNull(message = "이메일은 필수입니다.")
    private Email email;

    @Valid
    @NotNull(message = "비밀번호는 필수입니다.")
    private Password password;

    @Valid
    @NotNull(message = "닉네임은 필수입니다.")
    private Nickname nickname;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(RoleType.USER)
                .build();
    }
}
