package com.bside.backendapi.domain.userappt.mapper;

import com.bside.backendapi.domain.userappt.dto.request.JoinRequest;
import com.bside.backendapi.domain.userappt.dto.response.UserApptResponse;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserApptMapper {

    @Mapping(source = "id", target = "uaid")
    @Mapping(source = "appointment.title", target = "apTitle")
    @Mapping(source = "appointment.place", target = "apPlace")
    @Mapping(source = "appointment.appointmentType", target = "apType")
    @Mapping(source = "appointment.time", target = "apTime")
    @Mapping(target = "imageUrl", ignore = true)  // imageUrl은 따로 설정할 예정
    UserApptResponse toUserApptResponse(UserAppt userAppt);

    default UserApptResponse toUserApptResponseWithImages(UserAppt userAppt, List<UserAppt> sameAppointmentUserAppts) {
        List<String> imageUrls = sameAppointmentUserAppts.stream()
                .map(ua -> ua.getUser().getProfileImg())
                .collect(Collectors.toList());
        UserApptResponse response = toUserApptResponse(userAppt);
        response.setImageUrl(imageUrls);
        return response;
    }



    // UserApptResponse toUserApptResponse(UserAppt userAppt);

}
