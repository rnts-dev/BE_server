package com.bside.backendapi.domain.member.domain.persist;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.vo.Nickname;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.bside.backendapi.domain.member.util.GivenMember.toEntity;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest {

    @Test
    @DisplayName("회원 정보를 업데이트 한다.")
    void update() {
        // given
        Member updateMember = Member.builder()
                .nickname(Nickname.from("변경된닉네임"))
                .profileImage("empty.png")
                .build();

        // when : GivenMember 객체를 업데이트 한다.
        Member member = toEntity().update(updateMember);

        // then
        assertAll(() -> {
            assertEquals(member.getNickname().nickname(), updateMember.getNickname().nickname());
            assertEquals(member.getProfileImage(), updateMember.getProfileImage());
        });
    }

}