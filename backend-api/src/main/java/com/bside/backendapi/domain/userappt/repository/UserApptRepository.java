package com.bside.backendapi.domain.userappt.repository;

import com.bside.backendapi.domain.appointment.entity.Appointment;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserApptRepository extends JpaRepository<UserAppt, Long> {
    List<UserAppt> findByUser(User user);
    List<UserAppt> findByAppointment(Appointment appointment);
    UserAppt findUserApptById(Long uaid);
}
