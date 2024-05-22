package com.bside.backendapi.domain.userappt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserApptResponse {

    private Long id;

    private String apTitle;
    private String apPlace;

    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime apTime;
    private List<String> imageUrl;
}
