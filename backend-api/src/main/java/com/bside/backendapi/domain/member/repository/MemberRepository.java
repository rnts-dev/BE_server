package com.bside.backendapi.domain.member.repository;


import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.vo.LoginId;
import com.bside.backendapi.domain.member.vo.Mail;
import com.bside.backendapi.domain.member.vo.Nickname;
import com.bside.backendapi.domain.member.vo.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 일반 회원
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(final LoginId loginId);
    boolean existsByMail(final Mail mail);
    boolean existsByNickname(final Nickname nickname);

    Optional<Member> findByLoginId(final LoginId loginId);

    Optional<Member> findByMail(final Mail mail);

    Optional<Member> findBySocialIdAndSocialType(String kakaoId, SocialType socialType);
}
