
package com.zabara.messaging.homework.kafka;

import com.zabara.messaging.homework.kafka.practicaltask1.dto.RandomMessage;
import com.zabara.messaging.homework.kafka.taxi.dto.Taxi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Random;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaTemplate<String, RandomMessage> kafkaTemplate(ProducerFactory<String, RandomMessage> producerFactory,
                                                              ConcurrentKafkaListenerContainerFactory<String, RandomMessage> listenerContainerFactory) {


        final ContainerProperties containerProperties = listenerContainerFactory.getContainerProperties();
        containerProperties.setMissingTopicsFatal(false);

        return new KafkaTemplate<>(producerFactory);

    }


    @Bean
    public KafkaTemplate<String, Taxi> kafkaTaxiTemplate(ProducerFactory<String, Taxi> producerFactory,
                                                     ConcurrentKafkaListenerContainerFactory<String, Taxi> listenerContainerFactory) {


        final ContainerProperties containerProperties = listenerContainerFactory.getContainerProperties();
        containerProperties.setMissingTopicsFatal(false);

        return new KafkaTemplate<>(producerFactory);

    }
    @Bean
    public Random random(){
        return new Random();
    }
}
