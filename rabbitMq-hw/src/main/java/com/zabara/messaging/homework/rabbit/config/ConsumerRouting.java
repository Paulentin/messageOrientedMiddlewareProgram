package com.zabara.messaging.homework.rabbit.config;

import com.zabara.messaging.homework.rabbit.FailedReceiptConsumer;
import com.zabara.messaging.homework.rabbit.ReceiptConsumer;
import com.zabara.messaging.homework.rabbit.controller.Receipt;
import org.springframework.amqp.core.DeclarableCustomizer;
import org.springframework.amqp.core.Queue;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Consumer;

@Component
public class ConsumerRouting {
    @Bean
    public DeclarableCustomizer declarableCustomizer() {
        return declarable -> {
            if (declarable instanceof Queue queue) {
                if (queue.getName().equals("receipt-queue1")
                        || queue.getName().equals("receipt-queue2")) {
                    queue.removeArgument("x-dead-letter-exchange");
                    queue.removeArgument("x-dead-letter-routing-key");
                    queue.addArgument("x-dead-letter-exchange", "deadletter-exchange");

                }
            }
            return declarable;
        };
    }


    @Bean
    public Consumer<Message<Receipt>> queue1Sink(StreamBridge streamBridge) {
//        return new ReceiptConsumer("Consumer1", streamBridge);
//        DLQ simulation
        return in -> sleepWrapped(Duration.ofSeconds(5));

    }

    @Bean
    public Consumer<Message<Receipt>> queue2Sink(StreamBridge streamBridge) {
        return new ReceiptConsumer("Consumer2", streamBridge);
    }

    @Bean
    public Consumer<Message<Receipt>> retrySink(StreamBridge streamBridge) {
        return new ReceiptConsumer("RetryConsumer", streamBridge);
    }

    @Bean
    public Consumer<Message<Receipt>> failedSink() {
        return new FailedReceiptConsumer("failedMessagesConsumer");
    }

    @Bean
    public Consumer<Message<Receipt>> deadletterSink() {
        return in -> System.out.println("DEADLETTER!!!! " + in.toString());

//        return new FailedReceiptConsumer("deadMessagesConsumer");
    }

    private static void sleepWrapped(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException ignore) {
        }
    }
}
