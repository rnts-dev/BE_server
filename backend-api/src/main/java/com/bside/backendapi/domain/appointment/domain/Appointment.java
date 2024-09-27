package com.bside.backendapi.domain.appointment.domain;

import com.bside.backendapi.domain.appointment.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.vo.Location;
import com.bside.backendapi.domain.appointment.vo.Title;
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
    @Column(name = "appointment_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private Title title;

    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;

    private Long customAppointmentTypeId;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

//    @Column(nullable = false)
    private Location location;

    private Long penaltyId;

    @Builder
    private Appointment(Long id, Title title, AppointmentType appointmentType, Long customAppointmentTypeId,
                        LocalDateTime appointmentTime, Location location) {
        this.id = id;
        this.title = title;
        this.appointmentType = appointmentType;
        this.customAppointmentTypeId = customAppointmentTypeId;
        this.appointmentTime = appointmentTime;
        this.location = location;
    }

    public Appointment create(final Long creatorId, final Appointment appointment) {
        this.setCreatorId(creatorId);
        this.title = appointment.getTitle();
        this.appointmentType = appointment.getAppointmentType();
        this.customAppointmentTypeId = appointment.getCustomAppointmentTypeId();
        this.appointmentTime = appointment.getAppointmentTime();
        this.location = appointment.getLocation();
        return this;
    }

    public void deletedCustomAppointmentType() {
        this.customAppointmentTypeId = null;
        this.appointmentType = AppointmentType.ETC;
    }

    public void update(Appointment updateAppointment) {
        this.title = updateAppointment.getTitle();

        if (updateAppointment.getAppointmentType() != null) {
            this.appointmentType = updateAppointment.getAppointmentType();
            this.customAppointmentTypeId = null;
        }
        else if (updateAppointment.getCustomAppointmentTypeId() != null) {
            this.appointmentType = null;
            this.customAppointmentTypeId = updateAppointment.getCustomAppointmentTypeId();
        }
        else {
            this.appointmentType = AppointmentType.ETC;
            this.customAppointmentTypeId = null;
        }

        this.appointmentTime = updateAppointment.getAppointmentTime();
        this.location = updateAppointment.getLocation();
    }

    //패널티 추가
    public void addPenalty(final Long penaltyId) {
        this.penaltyId = penaltyId;
    }

}


