package com.bside.backendapi.domain.member.domain.persist;

import com.bside.backendapi.domain.member.domain.vo.*;
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
@SQLRestriction("activated = true") // @Where : deprecated
@Table(indexes = {
        @Index(name = "i_email", columnList = "email"),
        @Index(name = "i_nickname", columnList = "nickname")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;

    @Embedded
    @Column(name = "login_id")
    private LoginId loginId;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Nickname nickname;

    @Column(name = "profile_url")
    private String profileUrl;

    @Enumerated(value = EnumType.STRING)
    private Tendency tendency;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(name = "activated")
    private Boolean activated = true;

    // KAKAO, NAVER, GOOGLE
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    @Embedded
    private AgreeTerms agreeTerms;

    @Builder
    private Member(Long id, LoginId loginId, Email email, Password password, Nickname nickname, String profileUrl,
                   Tendency tendency, RoleType role, SocialType socialType, String socialId, AgreeTerms agreeTerms) {

        this.id = id;
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.tendency = tendency;
        this.role = role;
        this.activated = true;
        this.socialType = socialType;
        this.socialId = socialId;
        this.agreeTerms = agreeTerms;
    }

    // 비즈니스 로직 추가
    public Member encode(final PasswordEncoder passwordEncoder) {
        password = Password.encode(password.password(), passwordEncoder);
        return this;
    }

    public void update(final Member member, final PasswordEncoder passwordEncoder) {
        this.password = Password.encode(member.getPassword().password(), passwordEncoder);
        this.nickname = member.getNickname();
        this.profileUrl = member.getProfileUrl();
    }

    public void resetPassword(final Member member, final PasswordEncoder passwordEncoder) {
        this.password = Password.encode(member.getPassword().password(), passwordEncoder);
    }

    public void setTendency(final Member member) {
        this.tendency = member.getTendency();
    }

    public void allAgreed() {
        this.agreeTerms = new AgreeTerms();
        this.agreeTerms.setAllAgree();
    }

    public void delete() {
        activated = false;
        recordDeletedTime();
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
