package com.example.spring_hex_practive.service.checkService;

import com.example.spring_hex_practive.controller.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.service.util.SwitchTrainKind;
import com.example.spring_hex_practive.service.outboundApiDto.CheckResponse;
import com.example.spring_hex_practive.controller.dto.request.Stops;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.exception.DataNotFoundException;
import com.example.spring_hex_practive.model.TrainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CheckTrain {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TrainRepo trainRepo;

    @Value("${checkTrainAvailableURL}")
    String checkTrainAvailableUrl;

    public void checkTrainNoExist(List<Map<String, Object>> trainNameAndTimeList) throws DataNotFoundException {
        if (trainNameAndTimeList.isEmpty()) {
            throw new DataNotFoundException("車次不存在");
        }
    }

    public void checkTrainStopExist(List<Map<String, Object>> targetTrainNoAndTrainKindList) throws DataNotFoundException {
        if (targetTrainNoAndTrainKindList.isEmpty()) {
            throw new DataNotFoundException("站名不存在");
        }
    }

    public void checkTrainNoAvailable(Integer trainNo) throws CheckErrorException {

        String url = checkTrainAvailableUrl + trainNo;

        ResponseEntity<CheckResponse> responseEntity = restTemplate.getForEntity(url, CheckResponse.class);

        if (200 == responseEntity.getStatusCodeValue()) {
            CheckResponse checkResponse = responseEntity.getBody();
            if (!checkResponse.getStatus().equals("available")) {
                throwCheckTrainException("TrainNotAvailable", "Train is not available");
            }
        }
    }

    //-------------------------------------------------------------------------------------

    public void multipleTrainCheck(CreateTrainRequest request) throws CheckErrorException {

        List<Map<String, String>> errorList = new ArrayList<>();

        checkTrainNoExists(request, errorList);
        checkTrainKindInvalid(request, errorList);
        checkTrainStopsDuplicate(request, errorList);

        if (!errorList.isEmpty()) {
            throw new CheckErrorException(errorList);
        }
    }

    private List<Map<String, String>> checkTrainNoExists(CreateTrainRequest request, List<Map<String, String>> errorList) {
        if (trainRepo.findByTrainNo(request.getTrainNo()) != null) {
            Map<String, String> errorMessage = setErrorMessage("TrainNoExists", "Train No is exists");
            errorList.add(errorMessage);
        }
        return errorList;
    }

    private List<Map<String, String>> checkTrainKindInvalid(CreateTrainRequest request, List<Map<String, String>> errorList) {
        //找出對應車種代碼
        String trainKindCode = SwitchTrainKind.getKind(request.getTrainKind());
        //資料庫無 報錯
        if (trainRepo.findByTrainKind(trainKindCode).isEmpty()) {
            Map<String, String> errorMessage = setErrorMessage("TrainKindInvalid", "Train Kind is invalid");
            errorList.add(errorMessage);
        }
        return errorList;
    }

    private List<Map<String, String>> checkTrainStopsDuplicate(CreateTrainRequest request, List<Map<String, String>> errorList) {
        //取得輸入站名list
        List<String> trainNameList = request.getStops().stream().map(stop -> stop.getStopName()).collect(Collectors.toList());
        //對上面list做去重
        List<String> noDuplicateNameList = trainNameList.stream().distinct().collect(Collectors.toList());
        //原先list如與去重list不同 報錯
        if (trainNameList.size() != noDuplicateNameList.size()) {
            Map<String, String> errorMessage = setErrorMessage("TrainStopsDuplicate", "Train Stops is duplicate");
            errorList.add(errorMessage);
        }
        return errorList;
    }

    //--------------------------------------------------------------------------------------------------
    //地名南至北順序
    private final List<String> places = List.of("屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
            "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆");

    public void checkTrainStopsSorted(List<Stops> sortedStopsList) throws CheckErrorException {
        //取得用時間排序的地名list
        List<String>sortedStopsNameList=sortedStopsList.stream().map(stop -> stop.getStopName()).collect(Collectors.toList());

        List<Integer> stopNumbersList = new ArrayList<>();
        //找出地名對應的數字
        for (String stopName : sortedStopsNameList) {
            if (!places.contains(stopName)) {
                throwCheckTrainException("TrainStopPositionNotRight", "Train Stops [" + stopName + "] position is not exists");
            }
            stopNumbersList.add(places.indexOf(stopName));
        }
        //當數字保持有序 前後相減 正負會一致 相乘始終>0 但順序出錯會有 +*- -*+出現 出現負值
        for (int now = 0; now < stopNumbersList.size() - 2; now++) {
            if (0 > (stopNumbersList.get(now) - stopNumbersList.get(now + 1)) * (stopNumbersList.get(now + 1) - stopNumbersList.get(now + 2))) {
                throwCheckTrainException("TrainStopsNotSorted", "Train Stops is not sorted");
            }
        }
    }
    //-------------------------------------------------------------------------------all
    public void createTrainCheck(CreateTrainRequest request, List<Stops> sortedStopsList) throws CheckErrorException {
        checkTrainNoAvailable(request.getTrainNo());
        multipleTrainCheck(request);
        checkTrainStopsSorted(sortedStopsList);
    }

    //-------------------------------------------------------------------------------method
    private Map<String, String> setErrorMessage(String code, String message) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("code", code);
        errorMessage.put("message", message);
        return errorMessage;
    }

    private void throwCheckTrainException(String code, String message) throws CheckErrorException {
        List<Map<String, String>> errorList = new ArrayList<>();
        Map<String, String> errorMessage = setErrorMessage(code, message);
        errorList.add(errorMessage);
        throw new CheckErrorException(errorList);
    }
}

