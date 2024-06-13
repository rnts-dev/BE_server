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

//    // 현재 시간을 한국 표준시로 가져오기
//    ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
//
//    // ZonedDateTime을 LocalDateTime으로 변환
//    LocalDateTime localDateTimeInKorea = nowInKorea.toLocalDateTime();

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
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    private boolean isfirst = false;

    @Builder
    private Appointment(Long id, String title, AppointmentType appointmentType, LocalDateTime appointmentTime,
                        String location, String latitude, String longitude, boolean isfirst) {
        this.id = id;
        this.title = title;
        this.appointmentType = appointmentType;
        this.appointmentTime = appointmentTime;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isfirst = isfirst;
    }

    // 비즈니스 로직 추가

}

