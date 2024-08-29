package com.bside.backendapi.global.oauth.domain;

import com.bside.backendapi.domain.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User implements UserDetails, OAuth2User {

    private final Member member;
    private OAuth2Attributes oAuth2Attributes;

    // 일반 로그인
    public CustomOAuth2User(Member member) {
        this.member = member;
    }

    // OAuth 로그인
    public CustomOAuth2User(Member member, OAuth2Attributes oAuth2Attributes) {
        this.member = member;
        this.oAuth2Attributes = oAuth2Attributes;
    }

    public Long getId() {
        return member.getId();
    }

    // OAuth2User 메서드
    @Override
    public String getName() {
        return oAuth2Attributes.getOAuth2UserInfo().getSocialId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes.getOAuth2UserInfo().attributes;
    }

    // UserDetails 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(((GrantedAuthority) () -> member.getRole().name()));
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword().password();
    }

    @Override
    public String getUsername() {
        return member.getLoginId().loginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}