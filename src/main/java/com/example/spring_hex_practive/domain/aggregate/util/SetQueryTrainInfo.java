package com.example.spring_hex_practive.domain.aggregate.util;

import com.example.spring_hex_practive.iface.dto.request.Stops;
import com.example.spring_hex_practive.iface.dto.response.GetStopByTrainNoResponse;
import com.example.spring_hex_practive.iface.dto.response.TrainInfo;
import com.example.spring_hex_practive.domain.aggregate.util.MakeUuid;
import com.example.spring_hex_practive.domain.aggregate.util.SwitchTrainKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: Q3 set model separate
@Component
@Transactional(rollbackFor = Exception.class)
public class SetQueryTrainInfo {
    @Autowired
    private MakeUuid makeUuid;
    DateFormat formatter = new SimpleDateFormat("HH:mm");


    public GetStopByTrainNoResponse getStopByTrainNo(Integer trainNo, List<Map<String, Object>> trainNameAndTimeList) {
        String trainName = SwitchTrainKind.getName(trainNameAndTimeList.get(0).get("TRAIN_KIND").toString());

        List<Stops> stopsResponseList = new ArrayList<>();

        trainNameAndTimeList.forEach(trainMap -> {
            Stops stop = new Stops(
                    trainMap.get("NAME").toString(),
                    formatter.format(trainMap.get("TIME"))
            );
            stopsResponseList.add(stop);
        });

        return new GetStopByTrainNoResponse(trainNo, trainName, stopsResponseList);
    }

    public List<TrainInfo> getTrainByStop(List<Map<String, Object>> targetTrainNoAndTrainKindList) {

        List<TrainInfo> getTargetTrainList = new ArrayList<>();

        targetTrainNoAndTrainKindList.forEach(trainMap -> {
            TrainInfo trainInfo = new TrainInfo(
                    Integer.parseInt(trainMap.get("TRAIN_NO").toString()),
                    SwitchTrainKind.getName(trainMap.get("TRAIN_KIND").toString())
            );
            getTargetTrainList.add(trainInfo);
        });

        return getTargetTrainList;
    }

    //--------------------------------------------------------------commend -> create train and stop
//    public Train createTrain(CreateTrainRequest request) {
//        String trainUuid = makeUuid.getTrainUuid();
//
//        String trainKindCode = SwitchTrainKind.getKind(request.getTrainKind());
//
//        Train train = new Train(
//                trainUuid,
//                request.getTrainNo(),
//                trainKindCode);
//        return train;
//
//    }
//
//    public TrainStop createStop(String trainUuid, Stops stop, int seq) {
//
//        String stopUuid = makeUuid.getTrainStopUuid();
//
//        TrainStop trainStop = TrainStop.builder()
//                .uuid(stopUuid)
//                .trainUuid(trainUuid)
//                .seq(seq)
//                .name(stop.getStopName())
//                .time(LocalTime.parse(stop.getStopTime()))
//                .deleteFlag("N")
//                .build();
//
////        TrainStop trainStop = new TrainStop(
////                stopUuid,
////                trainUuid,
////                seq,
////                stop.getStopName(),
////                LocalTime.parse(stop.getStopTime()),
////                "N");
//        return trainStop;
//    }
}