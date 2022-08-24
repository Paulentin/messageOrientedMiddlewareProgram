package com.zabara.messaging.homework.jms.configuration;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;


@Configuration
@EnableJms
public class JMSConfig {
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

    @Bean
    @Qualifier("topicJmsTemplate")
    public JmsTemplate jmsTopicTemplate(ActiveMQConnectionFactory activeMQConnectionFactory) {
        final JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        return jmsTemplate;
    }
}
