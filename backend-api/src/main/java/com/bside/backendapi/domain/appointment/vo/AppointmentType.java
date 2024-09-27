package com.bside.backendapi.domain.appointment.vo;

import lombok.Getter;

@Getter
public enum AppointmentType {

    MEAL("식사", "https://kr.object.ncloudstorage.com/rnts/icon/type%3Dmeal%2C%20color%3Dblack.png"),
    HOBBY("취미", "https://kr.object.ncloudstorage.com/rnts/icon/type%3Dfootball%2C%20color%3Dblack.png"),
    MEET("모임", "https://kr.object.ncloudstorage.com/rnts/icon/type%3Dflash%2C%20color%3Dblack.png"),
    STUDY("스터디", "https://kr.object.ncloudstorage.com/rnts/icon/type%3Dbook%2C%20color%3Dblack.png"),
    FAMILY("가족", "https://kr.object.ncloudstorage.com/rnts/icon/type%3Dfamily%2C%20color%3Dblack.png"),
    DATE("데이트", "https://kr.object.ncloudstorage.com/rnts/icon/type%3Dheart%2C%20color%3Dblack.png"),
    ETC("기타", "https://kr.object.ncloudstorage.com/rnts/icon/type%3Detc%2C%20color%3Dblack.png");

    private final String typeName;
    private final String imageUrl;

    AppointmentType(String typeName, String imageUrl) {
        this.typeName = typeName;
        this.imageUrl = imageUrl;
    }
}
