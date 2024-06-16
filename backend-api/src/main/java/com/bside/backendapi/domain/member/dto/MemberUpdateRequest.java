package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.Name;
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

    @JsonProperty("name")
    private Name name;

    private String profileUrl;

    public static MemberUpdateRequest of(final Password password, final Nickname nickname
            , final Name name, final String profileUrl) {
        return new MemberUpdateRequest(password, nickname, name, profileUrl);
    }

    public Member toEntity() {
        return Member.builder()
                .password(password)
                .nickname(nickname)
                .name(name)
                .profileUrl(profileUrl)
                .build();
    }
}
