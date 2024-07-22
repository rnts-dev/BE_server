package com.bside.backendapi.domain.appointmentMember.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentMemberResponse {

    private Long uaid;
    private String apTitle;
    private String apPlace;
    private LocalDateTime apTime;
    private String apType;

    private List<String> imageUrl;

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

}
