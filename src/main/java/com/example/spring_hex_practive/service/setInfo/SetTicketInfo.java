package com.example.spring_hex_practive.service.setInfo;

import com.example.spring_hex_practive.controller.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.model.TrainRepo;
import com.example.spring_hex_practive.model.TrainTicketRepo;
import com.example.spring_hex_practive.model.entity.TrainTicket;
import com.example.spring_hex_practive.service.util.GetTicketPrice;
import com.example.spring_hex_practive.service.util.MakeUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// TODO: Q3 set model separate
@Component
@Transactional(rollbackFor = Exception.class)
public class SetTicketInfo {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private GetTicketPrice getTicketPrice;
    @Autowired
    private MakeUuid makeUuid;
    @Autowired
    private TrainTicketRepo trainTicketRepo;

    public void createTrainTicket(BuyTicketRequest request, String ticketNO) {
        TrainTicket trainTicket = new TrainTicket(
                ticketNO,
                trainRepo.findUuidByTrainNo(request.getTrainNo()),
                request.getFromStop(),
                request.getToStop(),
                request.getTakeDate(),
                getTicketPrice.getCurrentPrice()
        );
        trainTicketRepo.save(trainTicket);
    }
    //
//        TrainTicket trainTicket = new TrainTicket();
//
//        trainTicket.setTicketNo(ticketNO);
//        trainTicket.setTrainUuid(trainRepo.findUuidByTrainNo(request.getTrainNo()));
//        trainTicket.setFromStop(request.getFromStop());
//        trainTicket.setToStop(request.getToStop());
//        trainTicket.setTakeDate(request.getTakeDate());
//        trainTicket.setPrice(getTicketPrice.getCurrentPrice());
}
