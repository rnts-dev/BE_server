package com.bside.backendapi.domain.member.domain;

import com.bside.backendapi.domain.member.vo.*;
import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        Member member = Member.builder()
                .id(annotation.id())
                .loginId(LoginId.from(annotation.loginId()))
                .mail(Mail.from(annotation.email()))
                .password(Password.from(annotation.password()))
                .nickname(Nickname.from(annotation.nickname()))
                .role(RoleType.USER)
                .build();

        List<GrantedAuthority> role = AuthorityUtils.createAuthorityList(RoleType.USER.name());

        CustomOAuth2User userDetails = new CustomOAuth2User(member);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, annotation.password(), role));

        return SecurityContextHolder.getContext();
    }
}