package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetailsResponse implements Serializable {

    private Long id;
    private LoginId loginId;
    private Email email;
    private Name name;
    private Nickname nickname;
    private Tendency tendency;
    private int age;

    public static MemberDetailsResponse of(final Member member) {
        int memberYear = member.getBirth().getYear();
        int now = LocalDate.now().getYear();
        int age = now - memberYear;

        return new MemberDetailsResponse(
                member.getId(),
                member.getLoginId(),
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getTendency(),
                age);
    }
}
