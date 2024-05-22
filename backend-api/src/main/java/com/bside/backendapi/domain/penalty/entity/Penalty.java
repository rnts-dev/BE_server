package com.bside.backendapi.domain.penalty.entity;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.user_temp.entity.User_temp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Penalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalty_id")
    private int id;

    private String content;
    private PenaltyType penaltyType;
    private int PenaltyPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User_temp user;

    @OneToOne
    private Appointment appointment;


    @OneToMany(mappedBy = "penalty", fetch = FetchType.LAZY)
    private List<ReceivedPenalty> receivedPenalties = new ArrayList<>();

}
