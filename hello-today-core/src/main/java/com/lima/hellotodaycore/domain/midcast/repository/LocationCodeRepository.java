package com.lima.hellotodaycore.domain.midcast.repository;

import com.lima.hellotodaycore.domain.midcast.entity.MidCastLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCodeRepository extends JpaRepository<MidCastLog, String> {

  MidCastLog readMidCastLogByLogTypeAndLocation(String logType, String location);
}
