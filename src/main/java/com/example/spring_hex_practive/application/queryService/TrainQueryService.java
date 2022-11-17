package com.example.spring_hex_practive.application.queryService;

import com.example.spring_hex_practive.domain.aggregate.domainService.TrainCheckDomainService;
import com.example.spring_hex_practive.iface.dto.response.GetStopByTrainNoResponse;
import com.example.spring_hex_practive.iface.dto.response.TrainInfo;
import com.example.spring_hex_practive.exception.DataNotFoundException;
import com.example.spring_hex_practive.infra.TrainRepo;
import com.example.spring_hex_practive.infra.TrainStopRepo;
import com.example.spring_hex_practive.domain.aggregate.util.SetQueryTrainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// TODO: Q1 CQRS
@Service("queryService")
public class TrainQueryService {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;
    @Autowired
    private TrainCheckDomainService trainCheckDomainService;
    @Autowired
    private SetQueryTrainInfo setQueryTrainInfo;
    //取得火車停靠站資訊
    public GetStopByTrainNoResponse getStopByTrainNo(Integer trainNo) throws DataNotFoundException {

        List<Map<String, Object>> trainNameAndTimeList = trainStopRepo.findTrainNameAndTime(trainNo);

        trainCheckDomainService.checkTrainNoExist(trainNameAndTimeList);

        return setQueryTrainInfo.getStopByTrainNo(trainNo, trainNameAndTimeList);   //放entity repo domain service
    }

    //取得會在目標站停車的火車資訊
    public List<TrainInfo> getTrainByStop(String stopName) throws DataNotFoundException {

        List<Map<String, Object>> targetTrainNoAndTrainKindList = trainRepo.findTrainNoAndTrainKind(stopName);

        trainCheckDomainService.checkTrainStopExist(targetTrainNoAndTrainKindList);

        return setQueryTrainInfo.getTrainByStop(targetTrainNoAndTrainKindList);
    }
}
