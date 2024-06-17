package com.bside.backendapi.domain.member.domain.persist;

import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.global.security.principal.CustomUserDetails;
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

    public Optional<CustomUserDetails> findByIdWithDetails(Long memberId) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomUserDetails.class,
                                member.id.as("id"),
                                member.email,
                                member.role))
                        .from(member)
                        .where(member.id.eq(memberId))
                        .fetchOne());
    }

    @Override
    public Optional<CustomUserDetails> findUserDetailsByEmail(Email email) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomUserDetails.class,
                                member.id.as("id"),
                                member.email,
                                member.role))
                        .from(member)
                        .where(member.email.eq(email))
                        .fetchOne()
        );
    }
}