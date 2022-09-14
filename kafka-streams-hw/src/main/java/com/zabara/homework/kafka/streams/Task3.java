package com.zabara.homework.kafka.streams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class Task3 {
    @Bean
    @Autowired
    public KStream<String, String> kStreamTask3(
            final StreamsBuilder kStreamBuilder,
            @Value(value = "${topic.name.input.task3-1}") final String inputTopic1,
            @Value(value = "${topic.name.input.task3-2}") final String inputTopic2
    ) {
        final KStream<String, String> firstStream = kStreamBuilder
                .stream(inputTopic1);
        final KStream<String, String> secondStream = kStreamBuilder
                .stream(inputTopic2);

        return firstStream
                .join(secondStream, (leftValue, rightValue) -> leftValue + " " + rightValue,
                        JoinWindows.ofTimeDifferenceAndGrace(Duration.ofMinutes(1), Duration.ofSeconds(30)))
                .peek((key, val) -> log.warn("key: {} , value: {}", key, val));
    }
}