package com.bside.backendapi.domain.appointment.domain.persist;

import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.domain.vo.Location;
import com.bside.backendapi.domain.appointment.domain.vo.Title;
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

    @Embedded
    @Column(nullable = false)
    private Title title;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "appointment_type")
    private AppointmentType appointmentType;

    @ManyToOne
    @JoinColumn(name = "custom_appointment_type_id")
    private CustomAppointmentType customAppointmentType;

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    @Embedded
    @Column(nullable = false)
    private Location location;

    @Column(name = "is_first")
    private boolean isFirst = false;

    @Column(name = "penalty_id")
    private Long penaltyId;

    @Builder
    private Appointment(Long id, Title title, AppointmentType appointmentType, CustomAppointmentType customAppointmentType, LocalDateTime appointmentTime,
                        Location location, boolean isFirst) {
        this.id = id;
        this.title = title;
        this.appointmentType = appointmentType;
        this.customAppointmentType = customAppointmentType;
        this.appointmentTime = appointmentTime;
        this.location = location;
        this.isFirst = false;
    }

    public Appointment create(final AppointmentType appointmentType, final CustomAppointmentType customAppointmentType) {
        this.appointmentType = appointmentType;
        this.customAppointmentType = customAppointmentType;
        return this;
    }

    //패널티 추가
    public void addPenalty(final Long penaltyId) {
        this.penaltyId = penaltyId;
    }



    public void update(final Appointment updateAppointment) {
        this.title = updateAppointment.title;
        this.appointmentType = updateAppointment.appointmentType;
        this.appointmentTime = updateAppointment.appointmentTime;
        this.location = updateAppointment.location;
    }
}



