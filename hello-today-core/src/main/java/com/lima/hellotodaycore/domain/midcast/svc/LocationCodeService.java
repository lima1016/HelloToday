package com.lima.hellotodaycore.domain.midcast.svc;

import com.lima.hellotodaycore.domain.midcast.entity.MidCastLog;
import com.lima.hellotodaycore.domain.midcast.repository.LocationCodeRepository;
import com.lima.hellotodaycore.schedule.type.LogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocationCodeService {

  private final LocationCodeRepository codeRepository;

  @Autowired
  public LocationCodeService(LocationCodeRepository codeRepository) {
    this.codeRepository = codeRepository;
  }

  public MidCastLog getLocationCode(LogType logType) {
    String location = null;
    switch (logType) {
      case MID_FCST -> location = "전국";
//      case MID_LAND_FCST -> location = "서울, 인천, 경기도";
//      case MID_SEA_FCST -> location = "서해중부";
//      case MID_TA -> location = "서울";
    }
    log.info("logType >>> " + logType.name() + ", location >>> " + location);
    return codeRepository.readMidCastLogByLogTypeAndLocation(logType.name(), location);
  }
}
