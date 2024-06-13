package com.bside.backendapi.domain.memberAppointment.repository;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.member.entity.User;
import com.bside.backendapi.domain.memberAppointment.entity.UserAppt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserApptRepository extends JpaRepository<UserAppt, Long> {
    List<UserAppt> findByUser(User user);
    List<UserAppt> findByAppointment(Appointment appointment);
    UserAppt findUserApptById(Long uaid);
}
