package com.bside.backendapi.domain.member.repository;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.vo.LoginId;
import com.bside.backendapi.domain.member.vo.Mail;
import com.bside.backendapi.domain.member.vo.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    boolean existsByMail(final Mail mail);

    boolean existsByNickname(final Nickname nickname);

    boolean existsByLoginId(final LoginId loginId);

    Optional<Member> findByMail(final Mail mail);

    Optional<Member> findByLoginId(final LoginId loginId);
}