package com.example.kafkalistenerdemo.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Data {

  private Long sourceId;
  private LocalDateTime timestamp;
  private DataType type;
  private double value;

  public enum DataType {

    TEMPERATURE,
    VOLTAGE,
    POWER

  }

}
