package com.example.spring_hex_practive.service.queryService;

import com.example.spring_hex_practive.controller.dto.response.GetTargetTrainResponse;
import com.example.spring_hex_practive.controller.dto.response.TrainInfo;
import com.example.spring_hex_practive.exception.DataNotFoundException;
import com.example.spring_hex_practive.model.TrainRepo;
import com.example.spring_hex_practive.model.TrainStopRepo;
import com.example.spring_hex_practive.service.checkService.CheckTrain;
import com.example.spring_hex_practive.service.setInfo.SetTrainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

// TODO: Q1 CQRS
@Service("queryService")
public class QueryServiceImpl implements QueryService {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;
    @Autowired
    private CheckTrain checkTrain;
    @Autowired
    private SetTrainInfo setTrainInfo;
    DateFormat formatter = new SimpleDateFormat("HH:mm");

    @Override //取得火車停靠站資訊
    public GetTargetTrainResponse getTargetTrain(Integer trainNo) throws DataNotFoundException {

        List<Map<String, Object>> trainNameAndTimeList = trainStopRepo.findTrainNameAndTime(trainNo);

        checkTrain.checkTrainNoExist(trainNameAndTimeList);

        return setTrainInfo.getStopByTrainNo(trainNo,trainNameAndTimeList);
    }
    @Override //取得會在目標站停車的火車資訊
    public List<TrainInfo> getTrainByStop(String stopName) throws DataNotFoundException {

        List<Map<String, Object>> targetTrainNoAndTrainKindList = trainRepo.findTrainNoAndTrainKind(stopName);

        checkTrain.checkTrainStopExist(targetTrainNoAndTrainKindList);

        return setTrainInfo.getTrainByStop(targetTrainNoAndTrainKindList);
    }
}
