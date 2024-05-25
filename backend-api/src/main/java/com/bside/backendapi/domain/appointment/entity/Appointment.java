package com.bside.backendapi.domain.appointment.entity;

import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor() //access = AccessLevel.PROTECTED
@AllArgsConstructor()
public class Appointment {

    // 현재 시간을 한국 표준시로 가져오기
    ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    // ZonedDateTime을 LocalDateTime으로 변환
    LocalDateTime localDateTimeInKorea = nowInKorea.toLocalDateTime();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    private String title;


    private String appointmentType;
    private int count;
    private LocalDateTime time;
    private String latitude;
    private String longitude;
    private String place;
    private String apkey;

    private boolean isfirst = false;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private List<UserAppt> userAppts;


}
