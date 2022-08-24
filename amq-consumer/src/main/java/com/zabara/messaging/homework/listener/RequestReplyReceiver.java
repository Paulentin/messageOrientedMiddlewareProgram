package com.zabara.messaging.homework.listener;

import com.zabara.messaging.homework.jms.dto.ObjectRequest;
import com.zabara.messaging.homework.jms.dto.ObjectResponse;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class RequestReplyReceiver implements SessionAwareMessageListener<Message> {
    public static final String OBJECT_REQUEST_QUEUE = "object-request-queue";

    @Override
    @JmsListener(destination = OBJECT_REQUEST_QUEUE)
    public void onMessage(Message message, Session session) throws JMSException {
        ObjectRequest order = (ObjectRequest) ((ActiveMQObjectMessage) message).getObject();
        ObjectResponse shipment = ObjectResponse.builder()
                .id(order.getId())
                .message(order.getMessage())
                .price(order.getPrice() + 100)
                .status("OK")
                .build();

        final ObjectMessage responseMessage = new ActiveMQObjectMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        responseMessage.setObject(shipment);

        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        producer.send(responseMessage);
    }
}