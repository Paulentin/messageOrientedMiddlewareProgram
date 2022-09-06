package com.zabara.messaging.homework.kafka.practicaltask1;

import com.zabara.messaging.homework.kafka.practicaltask1.dto.RandomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AtMostOnceConsumer {


    @KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
    public void run(RandomMessage randomMessage) {
        log.info("Consumer recieved...");
        log.info(randomMessage.message());


    }
}
