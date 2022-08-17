package com.zabara.messaging.homework;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    @Bean
    Connection connection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        return connectionFactory.createConnection();
    }
}
