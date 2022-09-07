package com.zabara.messaging.homework.kafka.taxi;

import com.zabara.messaging.homework.kafka.taxi.dto.Taxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class TaxiController {
    private final List<TaxiCoordinatesProducer> taxiCoordinatesProducers;
    @Autowired
    private Random random;

    public TaxiController(TaxiCoordinatesProducer taxiCoordinatesProducer) {
        taxiCoordinatesProducers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            taxiCoordinatesProducers.add(taxiCoordinatesProducer);
        }
    }

    @PostMapping(path = "taxi")
    void sendMessage() {
        for (int i = 0; i < taxiCoordinatesProducers.size(); i++) {
            final Taxi taxi = new Taxi(i,
                    Double.parseDouble(String.format("%s%s", 50.3, random.ints(10000, 99999).findFirst().getAsInt())),
                    Double.parseDouble(String.format("%s%s", 30.5, random.ints(10000, 99999).findFirst().getAsInt())));
            taxiCoordinatesProducers.get(i).request(taxi);
        }
    }
}
