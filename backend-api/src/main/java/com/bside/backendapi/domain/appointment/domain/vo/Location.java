package com.bside.backendapi.domain.appointment.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location implements Serializable {
    @NotBlank(message = "장소를 입력하세요.")
    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @JsonValue
    public String place() {
        return place;
    }

    @JsonValue
    public Double latitude() {
        return latitude;
    }

    @JsonValue
    public Double longitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location location = (Location) obj;
        return Objects.equals(this.place, location.place) &&
                Objects.equals(this.latitude, location.latitude) &&
                Objects.equals(this.longitude, location.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place(), latitude(), longitude());
    }
}
