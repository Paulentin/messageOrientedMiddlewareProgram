package com.zabara.messaging.homework.jms.controller;

import com.zabara.messaging.homework.jms.producer.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JMSController {

    private final Publisher publisher;

    @PostMapping("/pub-to-topic")
    public void publishMsgToTopic(@RequestParam String message){
        publisher.sendMessageToQueue(message);
    }
}
