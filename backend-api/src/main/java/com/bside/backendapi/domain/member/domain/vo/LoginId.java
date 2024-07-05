package com.bside.backendapi.domain.member.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable // 엔티티의 속성으로 포함될 수 있는 클래스
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginId implements Serializable {
    @NotBlank(message = "아이디를 입력하세요.")
    @Column(nullable = false)
    private String loginId;

    public static LoginId from(final String loginId) {
        return new LoginId(loginId);
    }

    @JsonValue
    public String loginId() {
        return loginId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LoginId loginId = (LoginId) obj;
        return Objects.equals(loginId(), loginId.loginId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId());
    }
}
