package com.bside.backendapi.domain.member.domain.persist;

import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.bside.backendapi.domain.member.domain.persist.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory query;

    public Optional<CustomOAuth2User> findByIdWithDetails(Long memberId) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomOAuth2User.class,
                                member.id.as("id"),
                                member.loginId,
                                member.role))
                        .from(member)
                        .where(member.id.eq(memberId))
                        .fetchOne());
    }

    @Override
    public Optional<CustomOAuth2User> findUserDetailsByLoginId(LoginId loginId) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomOAuth2User.class,
                                member.id.as("id"),
                                member.loginId,
                                member.role,
                                member))
                        .from(member)
                        .where(member.loginId.eq(loginId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Member> findBySocialId(String socialId) {
        return Optional.ofNullable(
                query.select(Projections.constructor(Member.class,
                                member.id.as("id"),
                                member.email,
                                member.nickname))
                        .from(member)
                        .where(member.socialId.eq(socialId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<CustomOAuth2User> findByEmail(Email email) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomOAuth2User.class,
                                member.id.as("id"),
                                member.loginId,
                                member.role,
                                member))
                        .from(member)
                        .where(member.email.eq(email))
                        .fetchOne()
        );
    }
}