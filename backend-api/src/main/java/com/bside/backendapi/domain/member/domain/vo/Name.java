package com.bside.backendapi.domain.member.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Name implements Serializable {
    @Column(nullable = false, length = 13)
    @NotBlank(message = "이름을 입력하세요.")
    @Length(max = 13)
    private String name;

    public static Name from(final String name) {
        return new Name(name);
    }

    @JsonValue
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Name name = (Name) obj;
        return Objects.equals(name(), name.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name());
    }
}
