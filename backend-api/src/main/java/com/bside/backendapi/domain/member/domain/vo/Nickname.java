package com.bside.backendapi.domain.member.domain.vo;

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

@Embeddable // 엔티티의 속성으로 포함될 수 있는 클래스
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Nickname implements Serializable {
    @NotBlank(message = "닉네임을 입력하세요.")
    @Column(nullable = false)
    @Length(max = 16)
    @Pattern(regexp = "^[a-zA-Z_-]+$")
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
