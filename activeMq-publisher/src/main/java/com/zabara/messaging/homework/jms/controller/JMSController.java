package com.zabara.messaging.homework.jms.controller;

import com.zabara.messaging.homework.jms.dto.ObjectRequest;
import com.zabara.messaging.homework.jms.dto.ObjectResponse;
import com.zabara.messaging.homework.jms.producer.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;

@RestController
@RequiredArgsConstructor
public class JMSController {

    private final Publisher publisher;

    @PostMapping("/pub-to-topic")
    public void publishMsgToTopic(@RequestParam String message) {
        publisher.sendMessageToQueue(message);
    }

    @PostMapping("/request-reply")
    public ResponseEntity<ObjectResponse> sendRequestReply(@RequestBody ObjectRequest message) throws JMSException {
        return ResponseEntity.ok(publisher.sendWithReply(message));
    }
}
