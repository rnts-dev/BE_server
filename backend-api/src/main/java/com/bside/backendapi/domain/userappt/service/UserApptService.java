package com.bside.backendapi.domain.userappt.service;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.user.repository.UserRepository;
import com.bside.backendapi.domain.userappt.dto.response.CheckinResponse;
import com.bside.backendapi.domain.userappt.dto.response.UserApptResponse;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import com.bside.backendapi.domain.userappt.mapper.UserApptMapper;
import com.bside.backendapi.domain.userappt.repository.UserApptRepository;
import com.bside.backendapi.global.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    private final JwtUtil jwtUtil;

    public void createUserAppt(Long appointmentId, HttpServletRequest httpRequest){

        //현재 사용자 id 가져오기 jwt

        String token = jwtUtil.extractTokenFromHeader(httpRequest);
        log.info("appointment token {}", token);
        String userIdString = jwtUtil.getUserId(token);
        log.info("userIdString {}", userIdString);
        Long userid = Long.parseLong(userIdString);        //현재 사용자
        log.info("userid {}", userid);

        User user = userRepository.findById(userid).orElseThrow(NoSuchFieldError::new);
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(NoSuchFieldError::new);

        UserAppt userAppt = UserAppt.createUserAppt(user,appointment);
        userApptRepository.save(userAppt);

    }

    public List<UserApptResponse> findAllMyUserApptFuture(HttpServletRequest httpRequest){

        //현재 아이디 불렁오기
        String token = jwtUtil.extractTokenFromHeader(httpRequest);
        String userIdString = jwtUtil.getUserId(token);
        Long userid = Long.parseLong(userIdString);
        log.info("userid {}", userid);

        //현재 user 불러오기
        User user = userRepository.findById(userid).orElseThrow(NoSuchFieldError::new);
        //해당 유저 userappt 불러오기
        List<UserAppt> userAppts = userApptRepository.findByUser(user);

        LocalDateTime today = LocalDateTime.now();

        return userAppts.stream()
                .filter(userAppt -> userAppt.getAppointment().getTime().isAfter(today))
                .map(userAppt -> {
                    List<UserAppt> sameAppointmentUserAppts = userApptRepository.findByAppointment(userAppt.getAppointment());
                    return userApptMapper.toUserApptResponseWithImages(userAppt, sameAppointmentUserAppts);
                })
                .collect(Collectors.toList());
    }


    public List<UserApptResponse> findAllMyUserApptPast(HttpServletRequest httpRequest){

        //현재 아이디 불렁오기
        String token = jwtUtil.extractTokenFromHeader(httpRequest);
        String userIdString = jwtUtil.getUserId(token);
        Long userid = Long.parseLong(userIdString);
        log.info("userid {}", userid);

        //현재 user 불러오기
        User user = userRepository.findById(userid).orElseThrow(NoSuchFieldError::new);
        //해당 유저 userappt 불러오기
        List<UserAppt> userAppts = userApptRepository.findByUser(user);

        LocalDateTime today = LocalDateTime.now();

        return userAppts.stream()
                .filter(userAppt -> userAppt.getAppointment().getTime().isBefore(today))
                .map(userAppt -> {
                    List<UserAppt> sameAppointmentUserAppts = userApptRepository.findByAppointment(userAppt.getAppointment());
                    return userApptMapper.toUserApptResponseWithImages(userAppt, sameAppointmentUserAppts);
                })
                .collect(Collectors.toList());
    }


    public List<UserApptResponse> findAllMyUserAppt(HttpServletRequest httpRequest){

        //현재 아이디 불렁오기
        String token = jwtUtil.extractTokenFromHeader(httpRequest);
        log.info("appointment token {}", token);
        String userIdString = jwtUtil.getUserId(token);
        log.info("userIdString {}", userIdString);
        Long userid = Long.parseLong(userIdString);        //현재 사용자
        log.info("userid {}", userid);

        //현재 user 불러오기
        User user = userRepository.findById(userid).orElseThrow(NoSuchFieldError::new);
        //해당 유저 userappt 불러오기
        List<UserAppt> userAppts = userApptRepository.findByUser(user);

        return userAppts.stream()
                .map(userAppt -> {
                    List<UserAppt> sameAppointmentUserAppts = userApptRepository.findByAppointment(userAppt.getAppointment());
                    return userApptMapper.toUserApptResponseWithImages(userAppt, sameAppointmentUserAppts);
                })
                .collect(Collectors.toList());
    }


    public CheckinResponse checkIn(Long uaid, HttpServletRequest httpRequest){
        //현재 아이디 불렁오기
        String token = jwtUtil.extractTokenFromHeader(httpRequest);
        String userIdString = jwtUtil.getUserId(token);
        Long userid = Long.parseLong(userIdString);
        log.info("userid {}", userid);

        //현재 User 가져와
        User user = userRepository.findById(userid).orElseThrow();
        //chekin 요청한 userappt 가져와
        UserAppt userAppt = userApptRepository.findById(uaid).orElseThrow();

        //appointment 불러
        Appointment appointment = userAppt.getAppointment();
        //약속시간 불러오고
        LocalDateTime appointTime = appointment.getTime();

        //현재시간이랑 비교 해서 지각 여부
        LocalDateTime nowTime = LocalDateTime.now();
        boolean isLate = nowTime.isAfter(appointTime);
        log.info("isLate {}", isLate);

        //이미 체크인 됐으면 리턴
        if (userAppt.getArrivalTime() != null) {
            log.info("User already checked in at {}", userAppt.getArrivalTime());
            return new CheckinResponse(false, false, 0L, true);
        }
        //도착시간 set
        userAppt.setArrivalTime(nowTime);


        //1등으로 온사람 있는지 확인
        boolean isFirst = appointment.isIsfirst();
        log.info("isFirst {}", isFirst);

        //1등 여부
        boolean isRegistPenalty = false;
        if(!isFirst){ //1등 온사람 없으면
            isRegistPenalty = true;
            appointment.setIsfirst(true);   //1등 왔다 appointment에 설정
        }
        log.info("isRegistPenalty {}", isRegistPenalty);

        long timeDifference = ChronoUnit.MINUTES.between(appointTime, nowTime);
        log.info("timeDifference {}", timeDifference);

        //테이블 변경 사항 저장
        userApptRepository.save(userAppt);
        appointmentRepository.save(appointment);

        CheckinResponse checkinResponse = new CheckinResponse(isLate,isRegistPenalty,timeDifference, false);
        return checkinResponse;
    }

}