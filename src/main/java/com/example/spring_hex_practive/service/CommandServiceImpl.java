package com.example.spring_hex_practive.service;

import com.example.spring_hex_practive.controller.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.controller.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.controller.dto.request.Stops;
import com.example.spring_hex_practive.service.serviceRestTempleAPI.GetTicketPriceResponse;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.model.TrainRepo;
import com.example.spring_hex_practive.model.TrainStopRepo;
import com.example.spring_hex_practive.model.TrainTicketRepo;
import com.example.spring_hex_practive.model.entity.Train;
import com.example.spring_hex_practive.model.entity.TrainStop;
import com.example.spring_hex_practive.model.entity.TrainTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private RestTemplate restTemplate;

    @Value("${ticketPriceURL}")
    String ticketPriceUrl;

    //---------------------------------------------------------------------train
    @Override
    @Transactional
    public Map<String, String> CreateTrainInfo(CreateTrainRequest request) throws CheckErrorException {
//---------------------------------------------------------------------set Train
        //時間排序list
        List<Stops> sortedStopsList = request.getStops().stream().sorted(Comparator.comparing(Stops::getStopTime)).collect(Collectors.toList());

        multipleTrainCheck(request, sortedStopsList);

        String trainUuid = java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();

        Train train = setTrainInfo(trainUuid, request);

        trainRepo.save(train);
//---------------------------------------------------------------------set TrainStop
        int seq = 1;
        for (Stops stop : sortedStopsList) {

            TrainStop trainStop = setTrainStopInfo(trainUuid, stop);
            trainStop.setSeq(seq++);

            trainStopRepo.save(trainStop);
        }

        Map<String, String> response = new HashMap<>();
        response.put("uuid", trainUuid);
        return response;
    }

    //---------------------------------------------------------------------method
    private Train setTrainInfo(String trainUuid, CreateTrainRequest request) {
        String trainKindCode = SwitchTrainKind.getKind(request.getTrainKind());

        Train train = new Train();

        train.setUuid(trainUuid);
        train.setTrainNo(request.getTrainNo());
        train.setTrainKind(trainKindCode);
        return train;
    }

    private TrainStop setTrainStopInfo(String trainUuid, Stops stop) {
        String stopUuid = java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();
        TrainStop trainStop = new TrainStop();

        trainStop.setUuid(stopUuid);
        trainStop.setTrainUuid(trainUuid);
        trainStop.setName(stop.getStopName());
        trainStop.setTime(LocalTime.parse(stop.getStopTime()));
        trainStop.setDeleteFlag("N");

        return trainStop;
    }

    private void multipleTrainCheck(CreateTrainRequest request, List<Stops> sortedStopsList) throws CheckErrorException {
        checkTrain.checkTrainNoAvailable(request.getTrainNo());
        checkTrain.multipleTrainCheck(request);
        checkTrain.checkTrainStopsSorted(sortedStopsList);
    }

    //---------------------------------------------------------------------ticket
    @Override
    @Transactional
    public Map<String, String> buyTicket(BuyTicketRequest request) throws CheckErrorException {

        multipleTrainTicketCheck(request);

        String ticketNO = java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();

        TrainTicket trainTicket = setTrainTicketInfo(request, ticketNO);

        trainTicketRepo.save(trainTicket);

        Map<String, String> response = new HashMap<>();
        response.put("uuid", ticketNO);
        return response;
    }

    //-----------------------------------------------------------------------------------method
    private void multipleTrainTicketCheck(BuyTicketRequest request) throws CheckErrorException {
        checkTicket.checkTrainNoNoExists(request.getTrainNo());
        checkTicket.checkStopSeq(request);
    }

    private Double getTicketPrice() {
        ResponseEntity<GetTicketPriceResponse> responseEntity = restTemplate.getForEntity(ticketPriceUrl, GetTicketPriceResponse.class);
        return responseEntity.getBody().getString();
    }

    private TrainTicket setTrainTicketInfo(BuyTicketRequest request, String ticketNO) {

        TrainTicket trainTicket = new TrainTicket();

        trainTicket.setTicketNo(ticketNO);
        trainTicket.setTrainUuid(trainRepo.findUuidByTrainNo(request.getTrainNo()));
        trainTicket.setFromStop(request.getFromStop());
        trainTicket.setToStop(request.getToStop());
        trainTicket.setTakeDate(request.getTakeDate());
        trainTicket.setPrice(getTicketPrice());

        return trainTicket;
    }

}
