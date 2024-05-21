package com.bside.backendapi.domain.userappt.entity;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAppt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_appt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;



    private LocalTime arrivalTime;
    private boolean safe;
    private int rank;  //default = 0?

    private PenaltyType penaltyType;
    private int penaltyPrice;
    private String penaltyDetail;


}
