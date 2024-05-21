package com.bside.backendapi.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String socialId;
    private String password;
    private String username;
    private String email;
    //private MemberRole role;
    private String profileImg;
    private String thumbnailImg;
}
