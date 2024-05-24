package com.bside.backendapi.domain.userappt.service;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.user.repository.UserRepository;
import com.bside.backendapi.domain.userappt.dto.response.UserApptResponse;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import com.bside.backendapi.domain.userappt.mapper.UserApptMapper;
import com.bside.backendapi.domain.userappt.repository.UserApptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserApptService {

    private final UserApptRepository userApptRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserApptMapper userApptMapper;

    public void createUserAppt(Long appointmentId){

        //현재 사용자 id 가져오기 jwt
        Long userId = 2L;

        User user = userRepository.findById(userId).orElseThrow(NoSuchFieldError::new);
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(NoSuchFieldError::new);

        UserAppt userAppt = UserAppt.createUserAppt(user,appointment);
        userApptRepository.save(userAppt);

    }

    public List<UserApptResponse> findAllMyUserAppt(){
        //현재 아이디 불렁오기
        Long userId = 2L;

        //현재 user 불러오기
        User user = userRepository.findById(userId).orElseThrow(NoSuchFieldError::new);
        //해당 유저 userappt 불러오기
        List<UserAppt> userAppts = userApptRepository.findByUser(user);

        return userAppts.stream()
                .map(userAppt -> {
                    List<UserAppt> sameAppointmentUserAppts = userApptRepository.findByAppointment(userAppt.getAppointment());
                    return userApptMapper.toUserApptResponseWithImages(userAppt, sameAppointmentUserAppts);
                })
                .collect(Collectors.toList());

//        List<UserApptResponse> responseList = new ArrayList<>();
//        for (UserAppt userAppt : userAppts) {
//            responseList.add(userApptMapper.toUserApptResponse(userAppt));
//        }
//
//        log.info("responseList {}", responseList);
//        return responseList;
    }


}