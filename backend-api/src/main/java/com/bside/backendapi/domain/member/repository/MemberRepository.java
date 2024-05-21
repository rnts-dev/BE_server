package com.bside.backendapi.domain.member.repository;

import com.bside.backendapi.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findBySocialId(String socialId);
}
