//package com.bside.backendapi.domain.member.domain;
//
//import com.bside.backendapi.domain.member.domain.Member;
//import com.bside.backendapi.domain.member.domain.vo.*;
//import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
//    @Override
//    public SecurityContext createSecurityContext(WithAuthUser annotation) {
//        Member member = Member.builder()
//                .id(annotation.id())
//                .loginId(LoginId.from(annotation.loginId()))
//                .mail(Email.from(annotation.mail()))
//                .password(Password.from(annotation.password()))
//                .name(Name.from(annotation.name()))
//                .nickname(Nickname.from(annotation.nickname()))
//                .role(RoleType.USER)
//                .birth(LocalDate.of(1995, 10, 8))
//                .build();
//
//        List<GrantedAuthority> role = AuthorityUtils.createAuthorityList(RoleType.USER.name());
//
//        CustomOAuth2User userDetails = new CustomOAuth2User(member);
//
//        SecurityContextHolder.getContext().setAuthentication(
//                new UsernamePasswordAuthenticationToken(userDetails, member.getPassword(), role));
//
//        return SecurityContextHolder.getContext();
//    }
//}