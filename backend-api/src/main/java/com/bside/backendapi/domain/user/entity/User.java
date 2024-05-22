package com.bside.backendapi.domain.user.entity;

import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserAppt> userAppts = new ArrayList<>();

    private String socialId;
    private String password;
    private String username;
    private String email;
    //private MemberRole role;
    private String profileImg;
    private String thumbnailImg;


    //등록한 패널티
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Penalty> penalties = new ArrayList<>();
}
