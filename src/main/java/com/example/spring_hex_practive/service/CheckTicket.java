package com.example.spring_hex_practive.service;

import com.example.spring_hex_practive.controller.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.model.TrainRepo;
import com.example.spring_hex_practive.model.TrainStopRepo;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckTicket {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;

    public void checkTrainNoNoExists(Integer trainNo) throws CheckErrorException {
        if (trainRepo.findByTrainNo(trainNo) == null) {
            throwCheckTicketException("TrainNoNotExists", "Train No does not exists");
        }
    }

    public void checkStopSeq(BuyTicketRequest request) throws CheckErrorException {
        try {
            String trainUuid = trainRepo.findUuidByTrainNo(request.getTrainNo());
            //上車站號碼
            int fromStop = trainStopRepo.findTrainStopSeq(request.getFromStop(), trainUuid);
            //下車站號碼
            int toStop = trainStopRepo.findTrainStopSeq(request.getToStop(), trainUuid);
            //上車數字>下車數字 不合理
            if (fromStop >= toStop) {
                throwCheckTicketException("TicketStopsInvalid", "Ticket From & To is invalid");
            }
        } catch (AopInvocationException e) {
            throwCheckTicketException("StopsNameError", "Stop Name is Not Exist");
        }
    }
//------------------------------------------------------------------------------method
    private Map<String, String> setErrorMessage(String code, String message) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("code", code);
        errorMessage.put("message", message);
        return errorMessage;
    }

    private void throwCheckTicketException(String code, String message) throws CheckErrorException {
        List<Map<String, String>> errorList = new ArrayList<>();
        Map<String, String> map = setErrorMessage(code, message);
        errorList.add(map);
        throw new CheckErrorException(errorList);
    }
}
