package com.example.kafkalistenerdemo.service;

import com.example.kafkalistenerdemo.model.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaDataServiceImpl implements KafkaDataService{

  @Override
  public void handle(Data data) {

    log.info("Data object received: {}", data.toString());

  }

}
