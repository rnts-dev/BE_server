package com.bside.backendapi.domain.member.domain.persist;

import com.bside.backendapi.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    public Optional<CustomUserDetails> findByIdWithDetails(Long memberId) {
        return null;
    }
}
