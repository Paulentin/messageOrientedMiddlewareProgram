package com.zabara.messaging.homework.rabbit;

import com.zabara.messaging.homework.rabbit.controller.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ReceiptConsumer implements Consumer<Message<Receipt>> {
    private final String consumerName;
    @Autowired
    private final StreamBridge streamBridge;


    @Override
    public void accept(Message<Receipt> receiptMessage) {
        System.out.println(consumerName +
                ", payload=" + receiptMessage.getPayload() +
                ", headers=" + receiptMessage.getHeaders());
        var deathHeader = receiptMessage.getHeaders().get("x-death", List.class);
        var death = deathHeader != null && deathHeader.size() > 0
                ? (Map<String, Object>) deathHeader.get(0)
                : null;
        if (death != null && (long) death.get("count") > 2) {
            // giving up - don't send to DLX
            streamBridge.send("failed-out-0",
                    MessageBuilder
                            .withPayload(receiptMessage.getPayload())
                            .copyHeaders(receiptMessage.getHeaders())
                            .build());
            throw new ImmediateAcknowledgeAmqpException("Failed after 3 attempts");
        }
        throw new AmqpRejectAndDontRequeueException("Failed message");
    }
}
