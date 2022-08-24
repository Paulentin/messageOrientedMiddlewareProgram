package com.zabara.messaging.homework;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableJms
public class JMSConfiguration {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String brokerUser;


    @Value("${spring.activemq.password}")
    private String brokerPassword;


    @Bean
    public ActiveMQConnectionFactory connection() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(brokerUser);
        connectionFactory.setPassword(brokerPassword);
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }


    @Bean(name = "topicListenerFactory")
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("3-10");
        return factory;
    }

    @Bean(name = "durableTopicListenerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerTopicContainerFactory(
            ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSubscriptionDurable(true);
        factory.setPubSubDomain(true);
        factory.setClientId("durable");
        factory.setConcurrency("1");
        return factory;
    }

}
