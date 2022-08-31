package com.zabara.messaging.homework.rabbit.controller;

import com.zabara.messaging.homework.rabbit.FailedMessageDataStore;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


@RestController
@AllArgsConstructor
public class SupportMessageController {

    private final StreamBridge streamBridge;
    private FailedMessageDataStore failedMessageDataStore;

    @PostMapping("/messages/{amqpMessageId}/reprocess")
    public void sendNotification(@PathVariable String amqpMessageId) {
        failedMessageDataStore.getMessages().stream()
                .filter(isMessageId(amqpMessageId))
                .forEach(message ->
                        streamBridge.send("source-out-0",
                                MessageBuilder
                                        .withPayload(message.getPayload())
                                        .copyHeaders(message.getHeaders())
                                        .build())
                );
        failedMessageDataStore.getMessages().removeIf(isMessageId(amqpMessageId));


    }

    private Predicate<Message<Receipt>> isMessageId(String amqpMessageId) {
        return message -> Objects.equals(message.getHeaders().get("amqp_messageId"), amqpMessageId);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message<Receipt>>> getFailedMessages() {
        return ResponseEntity.ok(failedMessageDataStore.getMessages());
    }

    @PostMapping("/messages/{amqpMessageId}/discard")
    public ResponseEntity<Boolean> discardMessage(@PathVariable String amqpMessageId) {
        return ResponseEntity.ok(failedMessageDataStore.getMessages().removeIf(isMessageId(amqpMessageId)));

    }
}