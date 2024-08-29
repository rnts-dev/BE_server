package com.bside.backendapi.domain.member.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable // 엔티티의 속성으로 포함될 수 있는 클래스
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Email implements Serializable {
    @jakarta.validation.constraints.Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일을 입력하세요.")
    @Column(nullable = false)
    private String email;

    // 이메일 인증 여부
    @Column(nullable = false)
    private boolean status;

    public static Email from(final String email) {
        return new Email(email);
    }

    @JsonValue
    public String email() {
        return email;
    }

    @Builder
    public Email(String email) {
        this.email = email;
        this.status = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Email email = (Email) obj;
        return Objects.equals(email(), email.email());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email());
    }
}
