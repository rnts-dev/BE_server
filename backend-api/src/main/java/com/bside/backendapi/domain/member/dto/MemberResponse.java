package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private Long id;
    private LoginId loginId;
    private Nickname nickname;

    public static MemberResponse of(final Member member) {
        return new MemberResponse(member.getId(), member.getLoginId(), member.getNickname());
    }
}
