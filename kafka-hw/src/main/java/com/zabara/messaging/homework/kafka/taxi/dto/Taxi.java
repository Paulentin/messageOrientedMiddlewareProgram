package com.zabara.messaging.homework.kafka.taxi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Taxi {
    private Integer id;
    private Double latitude;
    private Double longitude;

}
