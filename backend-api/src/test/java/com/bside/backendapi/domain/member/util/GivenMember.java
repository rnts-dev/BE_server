//package com.bside.backendapi.domain.member.util;
//
//import com.bside.backendapi.domain.member.domain.persist.Member;
//import com.bside.backendapi.domain.member.domain.vo.*;
//
//import java.time.LocalDate;
//
//public class GivenMember {
//    public static final LoginId GIVEN_LOGINID = LoginId.from("givenID");
//    public static final Email GIVEN_EMAIL = Email.from("givenmail@naver.com");
//    public static final Password GIVEN_PASSWORD = Password.from("givenpw123!");
//    public static final Nickname GIVEN_NICKNAME = Nickname.from("givennick");
//    public static final Name GIVEN_NAME = Name.from("테스트이름");
//    public static final LocalDate GIVEN_BIRTH = LocalDate.of(1995, 10, 8);
//
//    public static Member toEntity() {
//        return Member.builder()
//                .loginId(GIVEN_LOGINID)
//                .email(GIVEN_EMAIL)
//                .password(GIVEN_PASSWORD)
//                .nickname(GIVEN_NICKNAME)
//                .name(GIVEN_NAME)
//                .birth(GIVEN_BIRTH)
//                .role(RoleType.USER)
//                .build();
//    }
//}
