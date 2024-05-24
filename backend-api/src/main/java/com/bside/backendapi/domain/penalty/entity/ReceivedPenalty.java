package com.bside.backendapi.domain.penalty.entity;


import com.bside.backendapi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor() //access = AccessLevel.PROTECTED
@AllArgsConstructor() //access = AccessLevel.PROTECTED
public class ReceivedPenalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "received_penalty_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String receivedUser;
    private LocalTime resTime;

}
