package com.example.spring_hex_practive.domain.aggregate.entity;

import com.example.spring_hex_practive.iface.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.iface.dto.request.Stops;
import com.example.spring_hex_practive.exception.CheckErrorException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TRAIN_STOP")
public class TrainStop {
    @Id
    @Column(name = "UUID")
    private String uuid;
    @Column(name = "TRAIN_UUID")
    private String trainUuid;
    @Column(name = "SEQ")
    private Integer seq;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TIME")
    private LocalTime time;
    @Column(name = "DELETE_FLAG")
    private String deleteFlag;

//----------------------------------------------------------------------

    private TrainStop(String stopUuid, String trainUuid, Stops stop, int seq) {

        this.uuid = stopUuid;
        this.trainUuid = trainUuid;
        this.seq = seq;
        this.name = stop.getStopName();
        this.time = LocalTime.parse(stop.getStopTime());
        this.deleteFlag = "N";
    }

    public static TrainStop create(String stopUuid, String trainUuid, Stops stop, int seq) {
        return new TrainStop(stopUuid, trainUuid, stop, seq);
    }
//----------------------------------------------------------------------

    public static void checkTrainStopsDuplicate(CreateTrainRequest request, CheckErrorException checkErrorException) {//List<Map<String, String>> errorList
        //取得輸入站名list
        List<String> trainNameList = request.getStops().stream().map(stop -> stop.getStopName()).collect(Collectors.toList());
        //對上面list做去重
        List<String> noDuplicateNameList = trainNameList.stream().distinct().collect(Collectors.toList());
        //原先list如與去重list不同 報錯
        if (trainNameList.size() != noDuplicateNameList.size()) {
//            Map<String, String> errorMessage = ExceptionHandler.setErrorMessage(ErrorInfo.TrainStopsDuplicate);//"TrainStopsDuplicate", "Train Stops is duplicate"
            checkErrorException.getErrorList().add(ErrorInfo.TrainStopsDuplicate);
        }
    }
}
