//package com.bside.backendapi.domain.appointment.util;
//
//import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
//import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
//import com.bside.backendapi.domain.appointment.domain.vo.Location;
//import com.bside.backendapi.domain.appointment.domain.vo.Title;
//
//import java.time.LocalDateTime;
//
//public class GivenAppointment {
//
//    public static Title GIVEN_TITLE = Title.from("테스트 약속");
//    public static AppointmentType GIVEN_TYPE = AppointmentType.HOBBY;
//    public static LocalDateTime GIVEN_TIME = LocalDateTime.now();
//    public static Location GIVEN_LOCATION = Location.from(
//            "테스트 약속 장소",
//            37.7749,
//            -122.4194);
//
//    public static Appointment toEntity() {
//        return Appointment.builder()
//                .title(GIVEN_TITLE)
//                .appointmentType(GIVEN_TYPE)
//                .appointmentTime(GIVEN_TIME)
//                .location(GIVEN_LOCATION)
//                .build();
//    }
//
//    public static Appointment toEntityWithId() {
//        return Appointment.builder()
//                .id(1L)
//                .title(GIVEN_TITLE)
//                .appointmentType(GIVEN_TYPE)
//                .appointmentTime(GIVEN_TIME)
//                .location(GIVEN_LOCATION)
//                .build();
//    }
//}