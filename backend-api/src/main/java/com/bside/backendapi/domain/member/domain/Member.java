package com.bside.backendapi.domain.member.domain;

import com.bside.backendapi.domain.member.vo.*;
import com.bside.backendapi.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;

    private LoginId loginId;

    private Password password;

    private Mail mail;

    private Nickname nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Tendency tendency;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    // KAKAO, NAVER, GOOGLE
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private AgreeTerms agreeTerms;

    @Builder
    private Member(Long id, LoginId loginId, Password password, Mail mail, Nickname nickname, String profileImage,
                   Tendency tendency, RoleType role, SocialType socialType, String socialId, AgreeTerms agreeTerms) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.mail = mail;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.tendency = tendency;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.agreeTerms = agreeTerms;
    }

    public Member encode(final PasswordEncoder passwordEncoder) {
        password = Password.encode(password.password(), passwordEncoder);
        return this;
    }

    public void update(final Member member) {
        if (member.getNickname() != null) this.nickname = member.getNickname();
        if (member.getProfileImage() != null) this.profileImage = member.getProfileImage();
        if(member.getTendency() != this.tendency) this.tendency = member.getTendency();
    }

    public void resetPassword(final Member member, final PasswordEncoder passwordEncoder) {
        this.password = Password.encode(member.getPassword().password(), passwordEncoder);
    }

    public void allAgreed() {
        this.agreeTerms = new AgreeTerms();
        this.agreeTerms.setAllAgree();
    }

    // 이 두 메서드는 Member 객체가 hash 기반 컬렉션에서 동작할 수 있도록 보장
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return Objects.equals(id, member.id);
    }

    // id를 기반으로 hash code 생성, 동일한 id를 가진 객체는 동일한 hash code 가짐
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
