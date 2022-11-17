package com.example.spring_hex_practive.domain.aggregate.entity;

import com.example.spring_hex_practive.iface.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.iface.dto.request.Stops;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.domain.aggregate.util.SwitchTrainKind;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRAIN")
public class Train {
    @Id
    @Column(name = "UUID")
    private String uuid;
    @Column(name = "TRAIN_NO")
    private Integer trainNo;
    @Column(name = "TRAIN_KIND")
    private String trainKind;
    //問題:單一檢核
    //問題:check function 該在哪被呼叫
    //--------------------------------------------------------------------------------------
    private Train(CreateTrainRequest request, String uuid) {

        this.uuid = uuid;
        this.trainNo = request.getTrainNo();
        this.trainKind = SwitchTrainKind.getKind(request.getTrainKind());
    }

    public static Train create(CreateTrainRequest request, String uuid) {
        //check放這
        return new Train(request, uuid);
    }
    //--------------------------------------------------------------------------------------
    public static void checkTrainKindInvalid(CreateTrainRequest request,CheckErrorException checkErrorException) {// List<Map<String, String>> errorList
        //先找出對應車種代碼,如資料庫無 報錯
        if (SwitchTrainKind.getKind(request.getTrainKind()) == null) {
//            Map<String, String> errorMessage = ExceptionHandler.setErrorMessage(ErrorInfo.TrainKindInvalid);
            checkErrorException.getErrorList().add(ErrorInfo.TrainKindInvalid);
        }
    }
    private static final List<String> places = List.of("屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
            "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆");

    public static void checkTrainStopsSorted(CreateTrainRequest request) throws CheckErrorException {
        //取得用時間排序的地名list
        List<String> sortedStopsNameList = request.getStops().stream().sorted(Comparator.comparing(Stops::getStopTime)).map(Stops::getStopName).collect(Collectors.toList());
        List<Integer> stopNumbersList = new ArrayList<>();
        //找出地名對應的數字
        for (String stopName : sortedStopsNameList) {
            if (!places.contains(stopName)) {
               throw new CheckErrorException(ErrorInfo.TrainStopNameNoExists);//"TrainStopPositionNotRight", "Train Stops [" + stopName + "] position is not exists"
            }
            stopNumbersList.add(places.indexOf(stopName));
        }
        //當數字保持有序 前後相減 正負會一致 相乘始終>0 但順序出錯會有 +*- -*+出現 出現負值
        for (int now = 0; now < stopNumbersList.size() - 2; now++) {
            if (0 > (stopNumbersList.get(now) - stopNumbersList.get(now + 1)) * (stopNumbersList.get(now + 1) - stopNumbersList.get(now + 2))) {
                throw new CheckErrorException(ErrorInfo.TrainStopsNotSorted);//"TrainStopsNotSorted", "Train Stops is not sorted"
            }
        }
    }
}
