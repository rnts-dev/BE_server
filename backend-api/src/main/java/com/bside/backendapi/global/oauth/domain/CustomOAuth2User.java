package com.bside.backendapi.global.oauth.domain;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.RoleType;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
public class CustomOAuth2User implements UserDetails, OAuth2User {

    private Long id;
    private LoginId loginId;
    private RoleType role;
    private Member member;
    private OAuth2Attributes oAuth2Attributes;

    // 일반 로그인
    public CustomOAuth2User(Long id, LoginId loginId, RoleType role, Member member) {
        this.id = id;
        this.loginId = loginId;
        this.role = role;
        this.member = member;
    }

    public CustomOAuth2User(Member member) {
        this.member = member;
    }

    // OAuth 로그인
    public CustomOAuth2User(Member member, OAuth2Attributes oAuth2Attributes) {
        this.member = member;
        this.oAuth2Attributes = oAuth2Attributes;
    }

    // OAuth2User 메서드
    @Override
    public String getName() {
        return oAuth2Attributes.getOauth2UserInfo().getNickname();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes.getOauth2UserInfo().attributes;
    }

    // UserDetails 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return member.getPassword().password();
    }

    @Override
    public String getUsername() {
        return loginId.loginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}