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

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mail implements Serializable {
    @jakarta.validation.constraints.Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일을 입력하세요.")
    @Column(nullable = false)
    private String mail;

    // 이메일 인증 여부
    @Column(nullable = false)
    private boolean status;

    public static Mail from(final String mail) {
        return new Mail(mail);
    }

    @JsonValue
    public String mail() {
        return mail;
    }

    @Builder
    public Mail(String mail) {
        this.mail = mail;
        this.status = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mail mail = (Mail) obj;
        return Objects.equals(mail(), mail.mail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail());
    }
}
