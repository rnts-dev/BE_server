package com.bside.backendapi.domain.member.dto;


import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.vo.LoginId;
import com.bside.backendapi.domain.member.vo.Mail;
import com.bside.backendapi.domain.member.vo.Nickname;
import com.bside.backendapi.domain.member.vo.Tendency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetailResponse {

    private Long id;
    private LoginId loginId;
    private Mail mail;
    private Nickname nickname;
    private Tendency tendency;
    private String profileImage;

    public static MemberDetailResponse of(final Member member) {
        return new MemberDetailResponse(
                member.getId(),
                member.getLoginId(),
                member.getMail(),
                member.getNickname(),
                member.getTendency(),
                member.getProfileImage());
    }
}
