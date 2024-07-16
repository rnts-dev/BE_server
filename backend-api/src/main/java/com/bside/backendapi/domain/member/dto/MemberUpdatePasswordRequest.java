package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdatePasswordRequest {

    @JsonProperty("email")
    private Email email;

    @JsonProperty("newPassword")
    private Password newPassword;

    @JsonProperty("confirmPassword")
    private Password confirmPassword;

    public static MemberUpdatePasswordRequest of(final Email email, final Password newPassword, final Password confirmPassword) {
        return new MemberUpdatePasswordRequest(email, newPassword, confirmPassword);
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(newPassword)
                .build();
    }
}
