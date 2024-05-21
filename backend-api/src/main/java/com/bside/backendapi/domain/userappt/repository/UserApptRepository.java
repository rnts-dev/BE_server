package com.bside.backendapi.domain.userappt.repository;

import com.bside.backendapi.domain.userappt.entity.UserAppt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApptRepository extends JpaRepository<UserAppt, Long> {

}
