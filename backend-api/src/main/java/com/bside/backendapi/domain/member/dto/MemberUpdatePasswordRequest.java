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

    @JsonProperty("password")
    private Password password;

    @JsonProperty("checkPassword")
    private Password checkPassword;

    public static MemberUpdatePasswordRequest of(final Email email, final Password password, final Password checkPassword) {
        return new MemberUpdatePasswordRequest(email, password, checkPassword);
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
