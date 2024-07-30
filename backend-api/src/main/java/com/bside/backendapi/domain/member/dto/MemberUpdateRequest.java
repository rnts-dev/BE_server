package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import com.bside.backendapi.domain.member.domain.vo.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateRequest {

    @JsonProperty("password")
    private Password password;

    @JsonProperty("nickname")
    private Nickname nickname;

    private String profileUrl;

    public static MemberUpdateRequest of(final Password password, final Nickname nickname, final String profileUrl) {
        return new MemberUpdateRequest(password, nickname, profileUrl);
    }

    public Member toEntity() {
        return Member.builder()
                .password(password)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .build();
    }
}
