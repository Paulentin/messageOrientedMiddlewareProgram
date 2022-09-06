package com.zabara.messaging.homework.kafka.practicaltask1;

import com.zabara.messaging.homework.kafka.practicaltask1.dto.RandomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public record AtLeastOnceProducer(
        KafkaTemplate<String, RandomMessage> kafkaTemplate,
        String requestTopic) {

    public AtLeastOnceProducer(KafkaTemplate<String, RandomMessage> kafkaTemplate, @Value("${kafka.request.topic}") String requestTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.requestTopic = requestTopic;
    }

    public void request(RandomMessage randomMessage) {
        log.info("submitting a request for regNumber=" + randomMessage.id());

//        var record = new ProducerRecord<>(requestTopic, randomMessage.id(), randomMessage);
        kafkaTemplate.send(requestTopic, randomMessage);
    }
}
