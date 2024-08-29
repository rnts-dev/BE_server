package com.bside.backendapi.domain.appointment.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable @Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location implements Serializable {
    @NotBlank(message = "장소를 입력하세요.")
    @Column(nullable = false)
    @JsonProperty("place")
    private String place;

    @Column(nullable = false)
    @JsonProperty("latitude")
    private Double latitude;

    @Column(nullable = false)
    @JsonProperty("longitude")
    private Double longitude;

    public static Location from(final String place, final Double latitude, final Double longitude) {
        return new Location(place, latitude, longitude);
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
        return Objects.hash(getPlace(), getLatitude(), getLongitude());
    }
}
