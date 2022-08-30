package com.zabara.messaging.homework.rabbit;

import com.zabara.messaging.homework.rabbit.controller.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class FailedReceiptConsumer implements Consumer<Message<Receipt>> {
    private final String consumerName;

    @Autowired
    private FailedMessageDataStore failedMessageDataStore;


    @Override
    public void accept(Message<Receipt> receiptMessage) {
        System.out.println(consumerName +
                ", payload=" + receiptMessage.getPayload() +
                ", headers=" + receiptMessage.getHeaders());

        failedMessageDataStore.putMessage(receiptMessage);
    }
}
