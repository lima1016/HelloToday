package com.lima.hellotodaycore.domain.midcast.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_mid_cast_log")
@Data
public class MidCastLog implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("log_no")
  private Long logNo;

  @JsonProperty("location_code")
  private String locationCode;

  @JsonProperty("location")
  private String location;

  @JsonProperty("log_type")
  private String logType;

}
