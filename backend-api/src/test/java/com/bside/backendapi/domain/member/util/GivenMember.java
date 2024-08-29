package com.bside.backendapi.domain.member.util;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.vo.*;

public class GivenMember {
    public static final LoginId GIVEN_LOGINID = LoginId.from("givenID");
    public static final Mail GIVEN_MAIL = Mail.from("givenmail@naver.com");
    public static final Password GIVEN_PASSWORD = Password.from("givenpw123!");
    public static final Nickname GIVEN_NICKNAME = Nickname.from("givennick");

    public static Member toEntity() {
        return Member.builder()
                .loginId(GIVEN_LOGINID)
                .mail(GIVEN_MAIL)
                .password(GIVEN_PASSWORD)
                .nickname(GIVEN_NICKNAME)
                .role(RoleType.USER)
                .build();
    }
}