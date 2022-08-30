package com.zabara.messaging.homework.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class HomeWorkExchange {


    public static void main(String[] args) {
        SpringApplication.run(HomeWorkExchange.class);
    }

    @Bean
    public FailedMessageDataStore failedMessageDataStore(){
        return new FailedMessageDataStore(new ArrayList<>());
    }
}
