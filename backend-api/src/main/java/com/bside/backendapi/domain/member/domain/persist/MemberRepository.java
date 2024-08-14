package com.bside.backendapi.domain.member.domain.persist;

import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import com.bside.backendapi.domain.member.domain.vo.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    boolean existsByEmail(final Email email);

    boolean existsByNickname(final Nickname nickname);

    boolean existsByLoginId(final LoginId loginId);

    Optional<Member> findMemberByEmail(final Email email);

    Optional<Member> findByLoginId(final LoginId loginId);

    // 소셜 ID와 소셜 타입으로 회원 조회
    Optional<Member> findBySocialIdAndSocialType(String socialId, SocialType socialType);
}