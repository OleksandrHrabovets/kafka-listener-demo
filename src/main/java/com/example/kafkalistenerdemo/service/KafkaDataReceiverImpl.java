package com.example.kafkalistenerdemo.service;

import com.example.kafkalistenerdemo.model.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

  private final KafkaReceiver<String, Object> kafkaReceiver;
  private final KafkaDataService kafkaDataService;
  private final ObjectMapper objectMapper;

  @PostConstruct
  private void init() {

    fetch();

  }

  @Override
  public void fetch() {

    kafkaReceiver.receive()
        .subscribe(r -> {
          Data data = null;
          try {
            data = objectMapper.readValue(r.value().toString(), Data.class);
          } catch (JsonProcessingException e) {
            log.error("error parsing json", e);
          }
          kafkaDataService.handle(data);
          r.receiverOffset().acknowledge();
        });

  }

}
