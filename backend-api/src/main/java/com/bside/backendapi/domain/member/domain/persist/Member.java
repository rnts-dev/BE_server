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
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@Where(clause = "activated = true")
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
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Name name;

    @Embedded
    private Nickname nickname;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(name = "profile_url")
    private String profileUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Tendency tendency;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(name = "activated")
    private Boolean activated = true;

    @Builder
    private Member(Long id, Email email, Password password, Name name, Nickname nickname,
                   LocalDate birth, String profileUrl, Tendency tendency, RoleType role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.profileUrl = profileUrl;
        this.tendency = tendency;
        this.role = role;
        this.activated = true;
    }

    // 비즈니스 로직 추가
    public Member encode(final PasswordEncoder passwordEncoder) {
        password = Password.encode(password.password(), passwordEncoder);
        return this;
    }
}
