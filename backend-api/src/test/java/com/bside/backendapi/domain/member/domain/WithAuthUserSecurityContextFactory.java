package com.bside.backendapi.domain.member.domain;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.*;
import com.bside.backendapi.global.security.principal.CustomOAuth2User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDate;
import java.util.List;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        Member member = Member.builder()
                .id(annotation.id())
                .email(Email.from(annotation.email()))
                .password(Password.from(annotation.password()))
                .name(Name.from(annotation.name()))
                .nickname(Nickname.from(annotation.nickname()))
                .role(RoleType.USER)
                .birth(LocalDate.of(1995, 10, 8))
                .build();

        List<GrantedAuthority> role = AuthorityUtils.createAuthorityList(RoleType.USER.name());

        CustomOAuth2User userDetails = CustomOAuth2User.of(member);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, annotation.password(), role));

        return SecurityContextHolder.getContext();
    }
}