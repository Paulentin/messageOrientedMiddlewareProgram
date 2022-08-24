package com.zabara.messaging.homework.jms.producer;

import com.zabara.messaging.homework.jms.dto.ObjectRequest;
import com.zabara.messaging.homework.jms.dto.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import java.util.UUID;

@Slf4j
@Component
public class Publisher {
    public static final String OBJECT_REQUEST_QUEUE = "object-request-queue";
    public static final String OBJECT_REPLY_2_QUEUE = "object-reply-2-queue";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    @Value("${activemq.topic}")
    private String topic;

    public void sendMessageToQueue(String messageText) {
        log.info("Sending message " + messageText + " to  topic - " + topic);
        jmsTemplate.convertAndSend(
                new ActiveMQTopic(topic),
                messageText);
    }

    public ObjectResponse sendWithReply(ObjectRequest request) throws JMSException {
        jmsTemplate.setReceiveTimeout(1000L);
        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);

        Session session = jmsMessagingTemplate.getConnectionFactory().createConnection()
                .createSession(false, Session.AUTO_ACKNOWLEDGE);

        request.setId(UUID.randomUUID());
        ObjectMessage objectMessage = session.createObjectMessage(request);

        objectMessage.setJMSCorrelationID(UUID.randomUUID().toString());
        objectMessage.setJMSReplyTo(new ActiveMQQueue(OBJECT_REPLY_2_QUEUE));
        objectMessage.setJMSCorrelationID(UUID.randomUUID().toString());
        objectMessage.setJMSExpiration(1000L);
        objectMessage.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);

        return jmsMessagingTemplate.convertSendAndReceive(new ActiveMQQueue(OBJECT_REQUEST_QUEUE),
                objectMessage, ObjectResponse.class);
    }
}
