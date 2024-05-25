package com.bside.backendapi.domain.user.entity;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserAppt> userAppts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Penalty> penalties;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReceivedPenalty> receivedPenalties;

    private String username;
    private Long kakaoId;
    private String nickName;
    private String profileImg;
    private String thumbnailImg;
    private String role;
}
