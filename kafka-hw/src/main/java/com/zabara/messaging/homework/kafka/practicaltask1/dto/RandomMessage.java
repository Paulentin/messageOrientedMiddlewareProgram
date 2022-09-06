package com.zabara.messaging.homework.kafka.practicaltask1.dto;


import static org.apache.commons.lang3.Validate.notBlank;

public record RandomMessage(String id, String message) {

    public RandomMessage(String id, String message) {
        this.id = notBlank(id, "Id is required");
        this.message = notBlank(message, "Message is required");
    }

}
