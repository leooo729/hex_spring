package com.example.spring_hex_practive.service.commandService;

import com.example.spring_hex_practive.controller.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.controller.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.controller.dto.request.Stops;
import com.example.spring_hex_practive.service.checkService.CheckTicket;
import com.example.spring_hex_practive.service.checkService.CheckTrain;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.model.TrainRepo;
import com.example.spring_hex_practive.model.TrainStopRepo;
import com.example.spring_hex_practive.model.TrainTicketRepo;
import com.example.spring_hex_practive.service.setInfo.SetTicketInfo;
import com.example.spring_hex_practive.service.setInfo.SetTrainInfo;
import com.example.spring_hex_practive.service.util.MakeUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Q1 CQRS
@Service("CommandService")
public class CommandServiceImpl implements CommandService {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;
    @Autowired
    private CheckTrain checkTrain;
    @Autowired
    private CheckTicket checkTicket;
    @Autowired
    private TrainTicketRepo trainTicketRepo;
    @Autowired
    private SetTrainInfo setTrainInfo;
    @Autowired
    private SetTicketInfo setTicketInfo;
    @Autowired
    private MakeUuid makeUuid;

    //---------------------------------------------------------------------train
    @Override
    public String CreateTrainInfo(CreateTrainRequest request) throws CheckErrorException {
        //時間排序list
        List<Stops> sortedStopsList = request.getStops().stream().sorted(Comparator.comparing(Stops::getStopTime)).collect(Collectors.toList());

        checkTrain.createTrainCheck(request, sortedStopsList);

        String trainUuid = makeUuid.getTrainUuid();

        setTrainInfo.createTrain(trainUuid, request);//set Train

        setTrainInfo.createTrainStop(trainUuid, sortedStopsList);//set TrainStop

        return trainUuid;
    }

    //---------------------------------------------------------------------ticket
    @Override
    public String buyTicket(BuyTicketRequest request) throws CheckErrorException {

        checkTicket.buyTicketCheck(request);

        String ticketNO = makeUuid.getTrainTicketUuid();

        setTicketInfo.createTrainTicket(request, ticketNO);

        return ticketNO;
    }
}


