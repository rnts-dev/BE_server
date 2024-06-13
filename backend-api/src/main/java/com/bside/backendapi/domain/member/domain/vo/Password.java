package com.bside.backendapi.domain.member.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 9)
    @Column(nullable = false)
    private String password;

    public static Password from(final String password) {
        return new Password(password);
    }

    public static Password encode(final String rawPassword, final PasswordEncoder passwordEncoder) {
//        validatedPassword(rawPassword);
        return new Password(passwordEncoder.encode(rawPassword));
    }

//    private static void validatedPassword(final String rawPassword) {
//        if (Objects.isNull(rawPassword) || rawPassword.isBlank()) {
//            throw new PasswordNullException(ErrorCode.PASSWORD_NULL_ERROR);
//        }
//    }

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
