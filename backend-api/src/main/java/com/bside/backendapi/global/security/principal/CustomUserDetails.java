package com.bside.backendapi.global.security.principal;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.RoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomUserDetails implements UserDetails, Serializable {

    @Getter
    private Long id;

    private Email email;
    private RoleType role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    // password는 Context에 포함되지 않는다.
    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return email.email();
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

    public static CustomUserDetails of(final Member member) {
        return new CustomUserDetails(member.getId(), member.getEmail(), member.getRole());
    }
}
