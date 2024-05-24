package com.bside.backendapi.domain.appointment.entity;

import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor() //access = AccessLevel.PROTECTED
@AllArgsConstructor()
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    private String title;

//    @Enumerated(EnumType.STRING)
    private String appointmentType;

    private int count;
    private LocalDateTime time;
    private String place;
    private String apkey;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private List<UserAppt> userAppts;


}
