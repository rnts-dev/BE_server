package com.bside.backendapi.domain.user.entity;

import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserAppt> userAppts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReceivedPenalty> receivedPenalties;

    private String name;
    private String socialId;
    private String password;
    private String username;
    private String email;
    //private MemberRole role;
    private String profileImg;
    private String thumbnailImg;
    private String role;

    //등록한 패널티
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Penalty> penalties;

    @Builder
    public User(Long id, List<UserAppt> userAppts, List<ReceivedPenalty> receivedPenalties, String name, String socialId,
                String password, String username, String email, String profileImg, String thumbnailImg, String role,
                List<Penalty> penalties) {
        this.id = id;
        this.userAppts = userAppts;
        this.receivedPenalties = receivedPenalties;
        this.name = name;
        this.socialId = socialId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.profileImg = profileImg;
        this.thumbnailImg = thumbnailImg;
        this.role = role;
        this.penalties = penalties;
    }

}

