package com.bside.backendapi.domain.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String socialId;
    private String password;
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
    private String profileImg;
    private String thumbnailImg;

}
