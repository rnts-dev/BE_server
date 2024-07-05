package com.bside.backendapi.domain.member.domain.persist;

import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;

import java.util.Optional;

public interface MemberCustomRepository {
    Optional<CustomOAuth2User> findByIdWithDetails(final Long memberId);

    Optional<CustomOAuth2User> findUserDetailsByLoginId(final LoginId loginId);

    Optional<Member> findByName(final String name);

}
