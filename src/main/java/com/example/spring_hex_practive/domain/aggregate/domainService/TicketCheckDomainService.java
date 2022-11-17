package com.example.spring_hex_practive.domain.aggregate.domainService;

import com.example.spring_hex_practive.domain.aggregate.domainService.ErrorInfo;
import com.example.spring_hex_practive.iface.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.infra.TrainRepo;
import com.example.spring_hex_practive.infra.TrainStopRepo;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketCheckDomainService {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;

    //--------------------------------------------------------------------------- check ticket
    public void checkTrainNoNoExists(Integer trainNo) throws CheckErrorException {
        if (trainRepo.findByTrainNo(trainNo) == null) {
            throw new CheckErrorException(ErrorInfo.TrainNoNotExists);//"TrainNoNotExists", "Train No does not exists"
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
                throw new CheckErrorException(ErrorInfo.TicketStopsInvalid);//"TicketStopsInvalid", "Ticket From & To is invalid"
            }
        } catch (AopInvocationException e) {
            throw new CheckErrorException(ErrorInfo.StopsNameError);//"StopsNameError", "Stop Name is Not Exist"
        }
    }

    public void buyTicketCheck(BuyTicketRequest request) throws CheckErrorException {
        checkTrainNoNoExists(request.getTrainNo());
        checkStopSeq(request);
    }
//------------------------------------------------------------------------------method


//    private void throwCheckTicketException(ErrorInfo errorInfo) throws CheckErrorException {
//        List<Map<String, String>> errorList = new ArrayList<>();
//        Map<String, String> errorMessage = new HashMap<>();
//        errorMessage.put("code",errorInfo.getCode());
//        errorMessage.put("message", errorInfo.getErrorMessage());
//        errorList.add(errorMessage);
//        throw new CheckErrorException(errorList);
//    }
}

//
//    private void throwCheckTicketException(String code, String message) throws CheckErrorException {
//        List<Map<String, String>> errorList = new ArrayList<>();
//        Map<String, String> errorMessage = new HashMap<>();
//        errorMessage.put("code", code);
//        errorMessage.put("message", message);
//        errorList.add(errorMessage);
//        throw new CheckErrorException(errorList);
//    }
