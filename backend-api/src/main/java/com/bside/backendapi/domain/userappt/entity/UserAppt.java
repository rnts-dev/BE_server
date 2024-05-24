package com.bside.backendapi.domain.userappt.entity;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor() //access = AccessLevel.PROTECTED
@AllArgsConstructor() //access = AccessLevel.PROTECTED
public class UserAppt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_appt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime arrivalTime;
    private boolean safe;

    @Enumerated(EnumType.STRING)
    private ArriveType arriveType;


    public static UserAppt createUserAppt(User user, Appointment appointment){
        UserAppt userAppt = new UserAppt();
        userAppt.setUser(user);
        userAppt.setAppointment(appointment);

        return userAppt;
    }

}
