package com.bside.backendapi.domain.appointment.entity;

import com.bside.backendapi.domain.userappt.entity.UserAppt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    private String title;
    private AppointmentType appointmentType;
    private int count;
    private LocalDate date;
    private LocalTime time;
    private String place;
    private String apkey;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_appt_id")
    private List<UserAppt> userAppts = new ArrayList<>();
}
