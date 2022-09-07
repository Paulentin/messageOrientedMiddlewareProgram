package com.zabara.messaging.homework.kafka.taxi;

import com.zabara.messaging.homework.kafka.practicaltask1.dto.RandomMessage;
import com.zabara.messaging.homework.kafka.taxi.dto.Taxi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public record TaxiCoordinatesProducer(
        KafkaTemplate<String, Taxi> kafkaTaxiTemplate,
        String requestTopic) {

    public TaxiCoordinatesProducer(KafkaTemplate<String, Taxi> kafkaTaxiTemplate, @Value("${kafka.taxi.request.topic}") String requestTopic) {
        this.kafkaTaxiTemplate = kafkaTaxiTemplate;
        this.requestTopic = requestTopic;
    }

    public void request(Taxi taxi) {
        log.info("submitting a request for regNumber=" + taxi.getId());

//        var record = new ProducerRecord<>(requestTopic, taxi.id(), taxi);
        kafkaTaxiTemplate.send(requestTopic, taxi);
    }
}
