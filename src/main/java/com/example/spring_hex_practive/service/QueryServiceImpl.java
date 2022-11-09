package com.example.spring_hex_practive.service;

import com.example.spring_hex_practive.controller.dto.request.Stops;
import com.example.spring_hex_practive.controller.dto.response.GetTargetTrainResponse;
import com.example.spring_hex_practive.controller.dto.response.TrainInfo;
import com.example.spring_hex_practive.exception.DataNotFoundException;
import com.example.spring_hex_practive.model.TrainRepo;
import com.example.spring_hex_practive.model.TrainStopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("queryService")
public class QueryServiceImpl implements QueryService {
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;
    @Autowired
    private CheckTrain checkTrain;
     DateFormat formatter=new SimpleDateFormat("HH:mm");

    @Override
    public GetTargetTrainResponse getTargetTrain(Integer trainNo) throws DataNotFoundException {

//        Train train = trainRepo.findByTrainNo(trainNo);

        List<Map<String, Object>> trainNameAndTimeList=trainStopRepo.findTrainNameAndTime(trainNo);

        checkTrain.checkTrainNoExist(trainNameAndTimeList);

        String trainName = SwitchTrainKind.getName(trainNameAndTimeList.get(0).get("TRAIN_KIND").toString());

        List<Stops> stopsResponseList = setStopsResponseList(trainNameAndTimeList);

        return new GetTargetTrainResponse(trainNo, trainName, stopsResponseList);
    }

    @Override
    public List<TrainInfo> getTrainByStop(String stopName) throws DataNotFoundException {
//      List<String> trainUuidList = trainStopRepo.findTrainUuidByName(stopName);
        List<Map<String, Object>> targetTrainNoAndTrainKindList = trainRepo.findTrainNoAndTrainKind(stopName);

        if (targetTrainNoAndTrainKindList.isEmpty()) {
            throw new DataNotFoundException("站名不存在");
        }

        List<TrainInfo> getTargetTrainList = setTargetTrainList(targetTrainNoAndTrainKindList);

        return getTargetTrainList;
    }

    //------------------------------------------------------------------------------------
    private List<Stops> setStopsResponseList(List<Map<String, Object>> trainNameAndTimeList) {//
        List<Stops> stopsResponseList = new ArrayList<>();

//        List<TrainStop> trainStopList = trainStopRepo.findByTrainUuid(train.getUuid());

        for (Map map : trainNameAndTimeList) {
            Stops stops = new Stops();

            stops.setStopName(map.get("NAME").toString());
            stops.setStopTime(formatter.format(map.get("TIME")));

            stopsResponseList.add(stops);
        }
        return stopsResponseList;
    }

    private List<TrainInfo> setTargetTrainList(List<Map<String, Object>> targetTrainNoAndTrainKindList) {

        List<TrainInfo> getTargetTrainList = new ArrayList<>();

        for (Map map : targetTrainNoAndTrainKindList) {
            TrainInfo trainInfo = new TrainInfo();

            //Train train = trainRepo.findByUuid(trainUuid);

            String trainName = SwitchTrainKind.getName(map.get("TRAIN_KIND").toString());

            trainInfo.setTrainNo(Integer.parseInt(map.get("TRAIN_NO").toString()));
            trainInfo.setTrainKind(trainName);

            getTargetTrainList.add(trainInfo);
        }
        return getTargetTrainList;
    }
}
