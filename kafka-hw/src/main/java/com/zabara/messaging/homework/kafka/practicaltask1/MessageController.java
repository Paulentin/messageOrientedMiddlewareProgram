package com.zabara.messaging.homework.kafka.practicaltask1;

import com.zabara.messaging.homework.kafka.practicaltask1.dto.RandomMessage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
@AllArgsConstructor
public class MessageController {

    private AtLeastOnceProducer producer;

    @PostMapping(path = "send")
    void sendMessage(@RequestBody RandomMessage message){
        producer.request(message);
    }

}
