package com.zabara.messaging.homework.rabbit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class ReceiptController {
    private static final String ROUTING_KEY_HEADER = "receipt";

    private final StreamBridge streamBridge;


    @PostMapping("/receipt/{table}")
    public void sendNotification(@RequestBody Receipt receipt, @PathVariable int table) {
//        System.out.println("recieved notification for table :" + table);
        if (table == 1) {
            simulate20CallsToRoutingQueue1(receipt, table);
        }
        streamBridge.send("source-out-0",
                MessageBuilder
                        .withPayload(receipt)
                        .setHeader(ROUTING_KEY_HEADER, "routing-queue" + table)
                        .build());

    }

    private void simulate20CallsToRoutingQueue1(Receipt receipt, int table) {
        IntStream.range(0, 20).forEach(i -> streamBridge.send("source-out-0",
                MessageBuilder
                        .withPayload(receipt)
                        .setHeader(ROUTING_KEY_HEADER, "routing-queue" + table)
                        .build()));
    }
}
