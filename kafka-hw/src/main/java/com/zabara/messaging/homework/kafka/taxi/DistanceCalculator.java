package com.zabara.messaging.homework.kafka.taxi;

import org.springframework.stereotype.Service;

@Service
public class DistanceCalculator {

    public double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
