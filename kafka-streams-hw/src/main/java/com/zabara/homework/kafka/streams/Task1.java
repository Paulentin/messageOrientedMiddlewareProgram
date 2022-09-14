package com.zabara.homework.kafka.streams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Task1 {
    @Bean
    @Autowired
    public KStream<String, String> kStreamTask1(
            final StreamsBuilder kStreamBuilder,
            @Value(value = "${topic.name.input.task1}") final String inputTopic,
            @Value(value = "${topic.name.output.task1}") final String outPutTopic
    ) {
        final KStream<String, String> stream = kStreamBuilder
                .stream(inputTopic);
        stream
                .mapValues(s -> s + " with kstream")
                .peek((key, val) -> log.warn("from: {} to {} key: {} , value: {}", inputTopic, outPutTopic, key, val))
                .to(outPutTopic, Produced.with(Serdes.String(), Serdes.String()));

        return stream;
    }
}
