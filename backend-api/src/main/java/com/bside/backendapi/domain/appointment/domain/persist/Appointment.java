package com.bside.backendapi.domain.appointment.domain.persist;

import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.domain.vo.Location;
import com.bside.backendapi.domain.appointment.domain.vo.Title;
import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Title title;

    @Column(name = "creator_id", nullable = false)
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

    @Builder
    private Appointment(Long id, Title title, Long creatorId, AppointmentType appointmentType, LocalDateTime appointmentTime,
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
    public Appointment create(final Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    // 수정할 때 수정사항에 입력하지 않은 값들은 null 로 덮어씌워지는건가? 생각해봐야함
    public void update(final Appointment updateAppointment) {
        if (updateAppointment.title != null) {
            this.title = updateAppointment.title;
        }
        if (updateAppointment.appointmentType != null) {
            this.appointmentType = updateAppointment.appointmentType;
        }
        if (updateAppointment.appointmentTime != null) {
            this.appointmentTime = updateAppointment.appointmentTime;
        }
        if (updateAppointment.location != null) {
            this.location = updateAppointment.location;
        }
    }

    public void delete() {
        this.isDeleted = true;
    }
}

