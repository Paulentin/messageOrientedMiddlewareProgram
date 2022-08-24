package com.zabara.messaging.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;

@SpringBootApplication
public class ActiveMqPublisherApplication {

    public static void main(String[] args) throws JMSException {
        SpringApplication.run(ActiveMqPublisherApplication.class);
    }

}
