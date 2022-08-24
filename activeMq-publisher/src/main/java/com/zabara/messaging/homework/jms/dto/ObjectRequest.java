package com.zabara.messaging.homework.jms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
public class ObjectRequest implements Serializable {

    UUID id;

    String message;

    int price;
}
