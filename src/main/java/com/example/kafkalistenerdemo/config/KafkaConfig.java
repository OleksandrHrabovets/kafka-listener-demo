package com.example.kafkalistenerdemo.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Configuration
@Slf4j
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String servers;

  @Value("${spring.kafka.consumer.groupId}")
  private String groupId;

  @Value("${spring.kafka.consumer.key-deserializer}")
  private String keyDeserializer;

  @Value("${spring.kafka.consumer.value-deserializer}")
  private String valueDeserializer;

  @Value("${topics}")
  private List<String> topics;

  @Bean
  ReceiverOptions<String, Object> receiverOptions() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
    props.put("spring.json.trusted.packages", "*");

    ReceiverOptions<String, Object> receiverOptions = ReceiverOptions.create(props);
    return receiverOptions.subscription(topics)
        .addAssignListener(partition -> log.info("assigned: {}", partition))
        .addRevokeListener(partition -> log.info("revoked: {}", partition));
  }

  @Bean
  public KafkaReceiver<String, Object> kafkaReceiver(
      ReceiverOptions<String, Object> receiverOptions) {
    return KafkaReceiver.create(receiverOptions);
  }

}
