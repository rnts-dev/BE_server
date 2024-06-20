package com.bside.backendapi.domain.appointment.domain.persist;

import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.domain.vo.Location;
import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "creatorId", nullable = false)
    private Long creatorId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "appointment_type", nullable = false)
    private AppointmentType appointmentType;

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    @Embedded
    @Column(nullable = false)
    private Location location;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "is_first")
    private boolean isFirst = false;

    @OneToMany(mappedBy = "appointment")
    private List<AppointmentMember> members;


    @Column(name = "penalty_id")
    private Long penaltyId;

    @Builder
    private Appointment(Long id, String title, Long creatorId, AppointmentType appointmentType, LocalDateTime appointmentTime,
                        Location location, boolean isDeleted, boolean isFirst) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.appointmentType = appointmentType;
        this.appointmentTime = appointmentTime;
        this.location = location;
        this.isDeleted = false;
        this.isFirst = false;
    }

    // 비즈니스 로직 추가

    //패널티 추가
    public void addPenalty(final Long penaltyId){
        this.penaltyId = penaltyId;
    }
}

