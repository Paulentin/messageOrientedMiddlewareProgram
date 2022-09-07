package com.zabara.messaging.homework.kafka.taxi;

import com.zabara.messaging.homework.kafka.taxi.dto.Taxi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class TaxiCoordinatesConsumer {

    @Autowired
    private DistanceCalculator distanceCalculator;
    private final Double consumerLatitude = 50.396592;
    private final Double consumerLongitude = 30.599705;

    @KafkaListener(topics = "${kafka.taxi.request.topic}", groupId = "${kafka.taxi.group.id}")
    public void onMessage1(ConsumerRecord<Integer, Taxi> taxiConsumerRecord) {
        logTaxiCoordinates(taxiConsumerRecord, 1);
    }

    @KafkaListener(topics = "${kafka.taxi.request.topic}", groupId = "${kafka.taxi.group.id}")
    public void onMessage2(ConsumerRecord<Integer, Taxi> taxiConsumerRecord) {
        logTaxiCoordinates(taxiConsumerRecord, 2);
    }

    @KafkaListener(topics = "${kafka.taxi.request.topic}", groupId = "${kafka.taxi.group.id}")
    public void onMessage3(ConsumerRecord<Integer, Taxi> taxiConsumerRecord) {
        logTaxiCoordinates(taxiConsumerRecord, 3);
    }


    private void logTaxiCoordinates(ConsumerRecord<Integer, Taxi> taxiConsumerRecord, int consumerId) {
        final Taxi value = taxiConsumerRecord.value();
        final double distance = distanceCalculator
                .calculateDistanceBetweenPoints(consumerLatitude, value.getLatitude(), consumerLongitude, value.getLongitude());
        log.info(String.format("Consumer# %s Taxi: %s distance: %s . Coord: %s , %s ", consumerId, value.getId(), distance, value.getLatitude(), value.getLongitude()));
    }
}
