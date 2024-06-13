package com.bside.backendapi.domain.userappt.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserApptResponse {

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
