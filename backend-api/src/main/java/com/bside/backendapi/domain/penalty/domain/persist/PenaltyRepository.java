package com.bside.backendapi.domain.penalty.domain.persist;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    List<Penalty> findByPenaltyCreatorId(Long penaltyCreatorId);


}