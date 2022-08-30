package com.zabara.messaging.homework.rabbit.controller;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Receipt {
    int totalPrice;
    Map<String, Integer> goods;
}
