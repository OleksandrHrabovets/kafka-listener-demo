package com.example.kafkalistenerdemo.service;

import com.example.kafkalistenerdemo.model.Data;

public interface KafkaDataService {

  void handle(Data data);

}
