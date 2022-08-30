package com.zabara.messaging.homework.rabbit.controller;

import com.zabara.messaging.homework.rabbit.FailedMessageDataStore;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@AllArgsConstructor
public class SupportMessageController {

    private final StreamBridge streamBridge;
    private FailedMessageDataStore failedMessageDataStore;

    @PostMapping("/messages/reprocess/{amqpMessageId}")
    public void sendNotification(@PathVariable String amqpMessageId) {
        failedMessageDataStore.getMessages().stream()
                .filter(message -> Objects.equals(message.getHeaders().get("amqp_messageId"), amqpMessageId))
                .forEach(message ->
                streamBridge.send("source-out-0",
                        MessageBuilder
                                .withPayload(message.getPayload())
                                .copyHeaders(message.getHeaders())
                                .build())
        );


    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message<Receipt>>> getFailedMessages() {
        return ResponseEntity.ok(failedMessageDataStore.getMessages());
    }
}