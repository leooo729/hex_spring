package com.example.spring_hex_practive.service.setInfo;

import com.example.spring_hex_practive.controller.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.controller.dto.request.Stops;
import com.example.spring_hex_practive.controller.dto.response.GetTargetTrainResponse;
import com.example.spring_hex_practive.controller.dto.response.TrainInfo;
import com.example.spring_hex_practive.model.TrainRepo;
import com.example.spring_hex_practive.model.TrainStopRepo;
import com.example.spring_hex_practive.model.entity.Train;
import com.example.spring_hex_practive.model.entity.TrainStop;
import com.example.spring_hex_practive.service.util.MakeUuid;
import com.example.spring_hex_practive.service.util.SwitchTrainKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: Q3 set model separate
@Component
@Transactional(rollbackFor = Exception.class)
public class SetTrainInfo {
    @Autowired
    private MakeUuid makeUuid;
    @Autowired
    private TrainRepo trainRepo;
    @Autowired
    private TrainStopRepo trainStopRepo;
    DateFormat formatter = new SimpleDateFormat("HH:mm");


    public GetTargetTrainResponse getStopByTrainNo(Integer trainNo, List<Map<String, Object>> trainNameAndTimeList) {
        String trainName = SwitchTrainKind.getName(trainNameAndTimeList.get(0).get("TRAIN_KIND").toString());

        List<Stops> stopsResponseList = new ArrayList<>();

        trainNameAndTimeList.forEach(trainMap -> {
            Stops stop = new Stops();

            stop.setStopName(trainMap.get("NAME").toString());
            stop.setStopTime(formatter.format(trainMap.get("TIME")));

            stopsResponseList.add(stop);
        });

        return new GetTargetTrainResponse(trainNo,trainName,stopsResponseList);
    }

    public List<TrainInfo> getTrainByStop(List<Map<String, Object>> targetTrainNoAndTrainKindList) {

        List<TrainInfo> getTargetTrainList = new ArrayList<>();

        targetTrainNoAndTrainKindList.forEach(trainMap -> {
            TrainInfo trainInfo = new TrainInfo();

            String trainName = SwitchTrainKind.getName(trainMap.get("TRAIN_KIND").toString());

            trainInfo.setTrainNo(Integer.parseInt(trainMap.get("TRAIN_NO").toString()));
            trainInfo.setTrainKind(trainName);

            getTargetTrainList.add(trainInfo);

        });

        return getTargetTrainList;
    }

    //--------------------------------------------------------------commend -> create train
    public void createTrain(String trainUuid, CreateTrainRequest request) {

        String trainKindCode = SwitchTrainKind.getKind(request.getTrainKind());

        Train train = new Train(
                trainUuid,
                request.getTrainNo(),
                trainKindCode);

        trainRepo.save(train);
    }

    public void createTrainStop(String trainUuid, List<Stops> sortedStopsList) {

        int seq = 1;
        for (Stops stop : sortedStopsList) {
            String stopUuid = makeUuid.getTrainStopUuid();

            TrainStop trainStop =new TrainStop(
                    stopUuid,
                    trainUuid,
                    seq++,
                    stop.getStopName(),
                    LocalTime.parse(stop.getStopTime()),
                    "N");

            trainStopRepo.save(trainStop);
        }
    }
}

//        Train train = new Train();
//
//        train.setUuid(trainUuid);
//        train.setTrainNo(request.getTrainNo());
//        train.setTrainKind(trainKindCode);

//        TrainStop trainStop = new TrainStop();
//
//        trainStop.setUuid(stopUuid);
//        trainStop.setTrainUuid(trainUuid);
//        trainStop.setSeq(seq);
//        trainStop.setName(stop.getStopName());
//        trainStop.setTime(LocalTime.parse(stop.getStopTime()));
//        trainStop.setDeleteFlag("N");