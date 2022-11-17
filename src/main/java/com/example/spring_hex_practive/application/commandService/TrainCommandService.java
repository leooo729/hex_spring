package com.example.spring_hex_practive.application.commandService;

import com.example.spring_hex_practive.domain.aggregate.domainService.TrainCheckDomainService;
import com.example.spring_hex_practive.iface.dto.request.CreateTrainRequest;

import com.example.spring_hex_practive.iface.dto.request.Stops;
import com.example.spring_hex_practive.domain.aggregate.entity.Train;
import com.example.spring_hex_practive.domain.aggregate.entity.TrainStop;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.infra.TrainRepo;
import com.example.spring_hex_practive.infra.TrainStopRepo;
import com.example.spring_hex_practive.infra.TrainTicketRepo;
import com.example.spring_hex_practive.domain.aggregate.util.SetQueryTrainInfo;
import com.example.spring_hex_practive.domain.aggregate.util.MakeUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Q1 CQRS
@Service("TrainCommandService")
public class TrainCommandService {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;
    @Autowired
    private TrainCheckDomainService trainCheckDomainService;
    @Autowired
    private TrainTicketRepo trainTicketRepo;
    @Autowired
    private SetQueryTrainInfo setQueryTrainInfo;
    @Autowired
    private MakeUuid makeUuid;

    //---------------------------------------------------------------------train
    public String CreateTrainAndStop(CreateTrainRequest request) throws CheckErrorException {

        trainCheckDomainService.createTrainCheck(request);
        //----------------------------------------------------------------------- create train
//        Train train = setTrainInfo.createTrain(request);
        String trainUuid = makeUuid.getTrainUuid();
        Train train=Train.create(request,makeUuid.getTrainUuid());
        trainRepo.save(train);

        //---------------------------------------------------------------------create ticket
        int seq = 1;

        List<Stops> sortedStopsList = request.getStops().stream().sorted(Comparator.comparing(Stops::getStopTime)).collect(Collectors.toList());

        for (Stops stop : sortedStopsList) {
            String stopUuid = makeUuid.getTrainStopUuid();
            TrainStop trainStop=TrainStop.create(stopUuid,trainUuid,stop,seq++);

//            TrainStop trainStop = setTrainInfo.createStop(train.getUuid(), stop, seq++);

            trainStopRepo.save(trainStop);
        }
        return train.getUuid();
    }

}


