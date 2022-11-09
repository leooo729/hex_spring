package com.example.spring_hex_practive.service;

import com.example.spring_hex_practive.controller.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.controller.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.exception.CheckErrorException;

import java.util.Map;

public interface CommandService {
    Map<String, String> CreateTrainInfo(CreateTrainRequest request) throws CheckErrorException;

    Map<String, String> buyTicket(BuyTicketRequest request) throws CheckErrorException;
}
