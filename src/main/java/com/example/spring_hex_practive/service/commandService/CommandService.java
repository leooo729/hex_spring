package com.example.spring_hex_practive.service.commandService;

import com.example.spring_hex_practive.controller.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.controller.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.exception.CheckErrorException;

import java.util.Map;

public interface CommandService {
    String CreateTrainInfo(CreateTrainRequest request) throws CheckErrorException;

    String buyTicket(BuyTicketRequest request) throws CheckErrorException;
}
