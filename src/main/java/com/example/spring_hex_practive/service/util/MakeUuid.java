package com.example.spring_hex_practive.service.util;

import com.example.spring_hex_practive.model.TrainRepo;
import com.example.spring_hex_practive.model.TrainStopRepo;
import com.example.spring_hex_practive.model.TrainTicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MakeUuid {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;
    @Autowired
    private TrainTicketRepo trainTicketRepo;

    String uuid;

    public String getTrainUuid() {
        do {
            uuid = getRandomUuid();
        } while (trainRepo.findByUuid(uuid) != null);
        return uuid;
    }

    public String getTrainStopUuid() {
        do {
            uuid = getRandomUuid();
        } while ( trainStopRepo.findByUuid(uuid) != null);
        return uuid;
    }

    public String getTrainTicketUuid() {
        do {
            uuid = getRandomUuid();
        } while (trainTicketRepo.findByTicketNo(uuid) != null);
        return uuid;
    }

    public String getRandomUuid() {
        uuid = java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid;
    }
}
