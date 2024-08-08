package com.bside.backendapi.domain.member.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Column(nullable = false)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "영어 대소문자, 숫자, 특수문자를 포함한 8 ~ 16자리 여야 합니다.")
    private String password;

    public static Password from(final String password) {
        return new Password(password);
    }

    public static Password encode(final String rawPassword, final PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(rawPassword));
    }

    @JsonValue
    public String password() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Password password = (Password) obj;
        return Objects.equals(password(), password.password());
    }

    @Override
    public int hashCode() {
        return Objects.hash(password());
    }
}
