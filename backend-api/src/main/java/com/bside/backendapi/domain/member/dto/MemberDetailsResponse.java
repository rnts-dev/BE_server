package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import com.bside.backendapi.domain.member.domain.vo.Tendency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetailsResponse implements Serializable {

    private Long id;
    private LoginId loginId;
    private Email email;
    private Nickname nickname;
    private Tendency tendency;

    public static MemberDetailsResponse of(final Member member) {

        return new MemberDetailsResponse(
                member.getId(),
                member.getLoginId(),
                member.getEmail(),
                member.getNickname(),
                member.getTendency());
    }
}
