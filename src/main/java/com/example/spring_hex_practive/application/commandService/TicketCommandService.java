package com.example.spring_hex_practive.application.commandService;

import com.example.spring_hex_practive.domain.aggregate.domainService.TicketCheckDomainService;
import com.example.spring_hex_practive.iface.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.infra.TrainRepo;
import com.example.spring_hex_practive.infra.TrainTicketRepo;
import com.example.spring_hex_practive.domain.aggregate.entity.TrainTicket;
import com.example.spring_hex_practive.application.outbound.TicketOutboundService;
import com.example.spring_hex_practive.domain.aggregate.util.MakeUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketCommandService {
    @Autowired
    private TicketCheckDomainService ticketCheckDomainService;
    @Autowired
    private TrainTicketRepo trainTicketRepo;
    @Autowired
    private MakeUuid makeUuid;
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TicketOutboundService ticketOutboundService;

    public String buyTicket(BuyTicketRequest request) throws CheckErrorException {

        ticketCheckDomainService.buyTicketCheck(request);

//        TrainTicket trainTicket = setTicketInfo.createTrainTicket(request); //修改save拉到這//充血模式 x貧血模式//
        TrainTicket trainTicket = TrainTicket.create(
                request,
                makeUuid.getTrainTicketUuid(),
                trainRepo.findUuidByTrainNo(request.getTrainNo()),
                ticketOutboundService.getTicketPrice()
        );

        trainTicketRepo.save(trainTicket);

        return trainTicket.getTicketNo();
    }
}
