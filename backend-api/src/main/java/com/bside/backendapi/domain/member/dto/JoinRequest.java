package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

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
    @NotNull(message = "이름은 필수입니다.")
    private Name name;

    @Valid
    @NotNull(message = "닉네임은 필수입니다.")
    private Nickname nickname;

    @NotNull(message = "생년월일은 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .role(RoleType.USER)
                .birth(birth)
                .build();
    }
}
