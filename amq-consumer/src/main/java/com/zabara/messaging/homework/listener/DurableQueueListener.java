package com.zabara.messaging.homework.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Slf4j
@Component
public class DurableQueueListener {


    @Value("${activemq.queues.durable}")
    private String topic;

    @JmsListener(destination = "${activemq.queues.durable}",
            containerFactory = "durableTopicListenerFactory",
            subscription = "durable")
    public void receiveMsqFromTopic(Message msg) throws JMSException {
        TextMessage textMessage = (TextMessage) msg;
        String message = textMessage.getText();
        log.info("durable listener received msg:" + message + " from topic: " + topic);
    }
}
