package com.bside.backendapi.domain.member.util;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.Name;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import com.bside.backendapi.domain.member.domain.vo.Password;

import java.time.LocalDate;

public class GivenMember {
    public static final Email GIVEN_EMAIL = Email.from("rnts@naver.com");
    public static final Password GIVEN_PASSWORD = Password.from("123456789");
    public static final Nickname GIVEN_NICKNAME = Nickname.from("rnts_turtle");
    public static final Name GIVEN_NAME = Name.from("KWON");
    public static final LocalDate GIVEN_BIRTH = LocalDate.of(1995, 10, 8);

    public static Member toEntity() {
        return Member.builder()
                .email(GIVEN_EMAIL)
                .password(GIVEN_PASSWORD)
                .nickname(GIVEN_NICKNAME)
                .name(GIVEN_NAME)
                .birth(GIVEN_BIRTH)
                .build();
    }
}
