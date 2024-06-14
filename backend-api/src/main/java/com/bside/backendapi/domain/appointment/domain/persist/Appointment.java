package com.bside.backendapi.domain.appointment.domain.persist;

import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import com.bside.backendapi.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "appointment_type", nullable = false)
    private AppointmentType appointmentType;

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "is_first")
    private boolean isFirst = false;

    @Builder
    private Appointment(Long id, String title, AppointmentType appointmentType, LocalDateTime appointmentTime,
                        String location, Double latitude, Double longitude, boolean isDeleted, boolean isFirst) {
        this.id = id;
        this.title = title;
        this.appointmentType = appointmentType;
        this.appointmentTime = appointmentTime;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isDeleted = false;
        this.isFirst = false;
    }

    // 비즈니스 로직 추가

}

