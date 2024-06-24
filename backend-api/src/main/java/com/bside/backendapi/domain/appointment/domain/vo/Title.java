package com.bside.backendapi.domain.appointment.domain.vo;

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
public class Title implements Serializable {

    @Length(min = 2, max = 30)
    @NotBlank(message = "title은 필수 값입니다.")
    @Column(unique = true, length = 40)
    private String title;

    public static Title from(final String title) {
        return new Title(title);
    }

    @JsonValue
    public String title() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Title title = (Title) obj;
        return Objects.equals(title(), title.title());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title());
    }
}
