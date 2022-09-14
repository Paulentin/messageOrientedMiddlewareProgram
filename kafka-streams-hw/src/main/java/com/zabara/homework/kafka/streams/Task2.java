package com.zabara.homework.kafka.streams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class Task2 {
    @Bean
    @Autowired
    public KStream<String, String> kStreamTask2(
            final StreamsBuilder kStreamBuilder,
            @Value(value = "${topic.name.input.task2}") final String inputTopic,
            @Value(value = "${topic.name.input.task2-short}") final String outPutTopicShort,
            @Value(value = "${topic.name.input.task2-long}") final String outPutTopicLong,
            @Value(value = "${topic.name.output.task2}") final String outPutTopic
    ) {
        final KStream<String, String> stream = kStreamBuilder
                .stream(inputTopic);
        stream
                .peek((key, val) -> log.warn("value: {}", val))
                .filter((key, value) -> value != null)
                .flatMap((k, v) ->
                        Arrays.stream(v.split(" "))
                                .map(s -> new KeyValue<>(null, s))
                                .collect(Collectors.toList())
                )
                .map((key, value) -> new KeyValue<>(String.valueOf(value.length()), value))
                .peek((key, val) -> log.warn("from: {} to {} key: {} , value: {}", inputTopic, outPutTopic, key, val))
                .split(Named.as("words-"))
                .branch((key, value) -> value.length() > 10, Branched.withConsumer(ks -> ks.to(outPutTopicLong), "long"))
                .defaultBranch(Branched.withConsumer(ks -> ks.to(outPutTopicShort), "short"));


        return stream;
    }

    @Bean
    @Autowired
    public KStream<String, String> kStreamFilter(final StreamsBuilder kstreamsBuilder,
                                                 @Value(value = "${topic.name.output.task2-short}") final String inputTopicShort,
                                                 @Value(value = "${topic.name.output.task2-long}") final String inputTopicLong,
                                                 @Value(value = "${topic.name.output.task2}") final String outPutTopic
    ) {
        final KStream<String, String> streamShort = kstreamsBuilder.stream(inputTopicShort);
        final KStream<String, String> streamLong = kstreamsBuilder.stream(inputTopicLong);
        final KStream<String, String> mergedStream = streamShort.merge(streamLong);

        mergedStream
                .filter((key, value) -> value.contains("a"))
                .peek((key, val) -> log.warn("Merged topics key: {} , value: {}", key, val))
                .to(outPutTopic);
        return mergedStream;
    }
}
