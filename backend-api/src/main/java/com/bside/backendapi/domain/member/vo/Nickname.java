package com.bside.backendapi.domain.member.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Nickname implements Serializable {
    @NotBlank(message = "닉네임을 입력하세요.")
    @Column(nullable = false)
    @Length(max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_-]+$",
            message = "영어 대소문자, 한글 및 _(언더스코어), -(하이픈)만 사용 가능합니다.")
    private String nickname;

    public static Nickname from(final String nickname) {
        return new Nickname(nickname);
    }

    @JsonValue
    public String nickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Nickname nickname = (Nickname) obj;
        return Objects.equals(nickname(), nickname.nickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname());
    }
}
