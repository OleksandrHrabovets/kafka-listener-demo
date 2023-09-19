package com.example.kafkalistenerdemo.service;

import com.example.kafkalistenerdemo.model.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDataReceiverAnotation {

  private final KafkaDataService kafkaDataService;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "data-temperature")
  public void fetch(ConsumerRecord<String, Object> r, Acknowledgment acknowledgment) {

    Data data = null;
    try {
      data = objectMapper.readValue(r.value().toString(), Data.class);
    } catch (JsonProcessingException e) {
      log.error("error parsing json", e);
    }
    kafkaDataService.handle(data);
    acknowledgment.acknowledge();

  }

}
