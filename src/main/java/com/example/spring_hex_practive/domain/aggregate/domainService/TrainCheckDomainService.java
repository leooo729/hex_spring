package com.example.spring_hex_practive.domain.aggregate.domainService;

import com.example.spring_hex_practive.iface.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.domain.aggregate.entity.Train;
import com.example.spring_hex_practive.domain.aggregate.entity.TrainStop;
import com.example.spring_hex_practive.application.outbound.TrainOutboundService;
import com.example.spring_hex_practive.domain.outbound.dto.CheckResponse;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.exception.DataNotFoundException;
import com.example.spring_hex_practive.infra.TrainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TrainCheckDomainService {

    //    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private TrainRepo trainRepo;
    //    @Autowired
//    private TrainStopRepo trainStopRepo;
//    @Value("${checkTrainAvailableURL}")
//    String checkTrainAvailableUrl;
    @Autowired
    private TrainOutboundService trainOutboundService;

    public void checkTrainNoExist(List<Map<String, Object>> trainNameAndTimeList) throws DataNotFoundException {
        if (trainNameAndTimeList.isEmpty()) {
            throw new DataNotFoundException(ErrorInfo.TrainNoNotExistsCN.getErrorMessage());//"車次不存在"
        }
    }

    public void checkTrainStopExist(List<Map<String, Object>> targetTrainNoAndTrainKindList) throws DataNotFoundException {
        if (targetTrainNoAndTrainKindList.isEmpty()) {
            throw new DataNotFoundException(ErrorInfo.TrainNameNotExistsCN.getErrorMessage());//"站名不存在"
        }
    }

    public void checkTrainNoAvailable(Integer trainNo) throws CheckErrorException {
        ResponseEntity<CheckResponse> responseEntity = trainOutboundService.checkTrainNoAvailable(trainNo);

        if (200 == responseEntity.getStatusCodeValue()) {
            CheckResponse checkResponse = responseEntity.getBody();
            if (!checkResponse.getStatus().equals("available")) {
                throw new CheckErrorException(ErrorInfo.TrainNotAvailable);//"TrainNotAvailable", "Train is not available"
            }
        }
    }

    //-------------------------------------------------------------------------------------

    public void multipleTrainCheck(CreateTrainRequest request) throws CheckErrorException {

        CheckErrorException checkErrorException=new CheckErrorException();
        checkTrainNoExists(request, checkErrorException);
        Train.checkTrainKindInvalid(request, checkErrorException);
        TrainStop.checkTrainStopsDuplicate(request, checkErrorException);

        if (!checkErrorException.getErrorList().isEmpty()) {
            throw  checkErrorException;
        }
    }

    private void checkTrainNoExists(CreateTrainRequest request,CheckErrorException checkErrorException) {
        if (trainRepo.findByTrainNo(request.getTrainNo()) != null) {
//            Map<String, String> errorMessage = setErrorMessage(ErrorInfo.TrainNoExists);//"TrainNoExists", "Train No is exists"
            checkErrorException.getErrorList().add(ErrorInfo.TrainNoExists);
        }
    }

//    private List<Map<String, String>> checkTrainKindInvalid(CreateTrainRequest request, List<Map<String, String>> errorList) {
//        //找出對應車種代碼
//        String trainKindCode = SwitchTrainKind.getKind(request.getTrainKind());
//        if (trainKindCode == null) {
//            Map<String, String> errorMessage = setErrorMessage(ErrorInfo.TrainKindInvalid);//"TrainKindInvalid", "Train Kind is invalid"
//            errorList.add(errorMessage);
//        }
//        return errorList;
//    }

//    private void checkTrainStopsDuplicate(CreateTrainRequest request, List<Map<String, String>> errorList) {
//        //取得輸入站名list
//        List<String> trainNameList = request.getStops().stream().map(stop -> stop.getStopName()).collect(Collectors.toList());
//        //對上面list做去重
//        List<String> noDuplicateNameList = trainNameList.stream().distinct().collect(Collectors.toList());
//        //原先list如與去重list不同 報錯
//        if (trainNameList.size() != noDuplicateNameList.size()) {
//            Map<String, String> errorMessage = setErrorMessage(ErrorInfo.TrainStopsDuplicate);//"TrainStopsDuplicate", "Train Stops is duplicate"
//            errorList.add(errorMessage);
//        }
//    }

    //--------------------------------------------------------------------------------------------------
    //地名南至北順序
//    private final List<String> places = List.of("屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
//            "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆");
//
//    public void checkTrainStopsSorted(CreateTrainRequest request) throws CheckErrorException {
//        //取得用時間排序的地名list
//        List<String> sortedStopsNameList = request.getStops().stream().sorted(Comparator.comparing(Stops::getStopTime)).map(Stops::getStopName).collect(Collectors.toList());
//        List<Integer> stopNumbersList = new ArrayList<>();
//        //找出地名對應的數字
//        for (String stopName : sortedStopsNameList) {
//            if (!places.contains(stopName)) {
//                throwCheckTrainException(ErrorInfo.TrainStopNameNoExists);//"TrainStopPositionNotRight", "Train Stops [" + stopName + "] position is not exists"
//            }
//            stopNumbersList.add(places.indexOf(stopName));
//        }
//        //當數字保持有序 前後相減 正負會一致 相乘始終>0 但順序出錯會有 +*- -*+出現 出現負值
//        for (int now = 0; now < stopNumbersList.size() - 2; now++) {
//            if (0 > (stopNumbersList.get(now) - stopNumbersList.get(now + 1)) * (stopNumbersList.get(now + 1) - stopNumbersList.get(now + 2))) {
//                throwCheckTrainException(ErrorInfo.TrainStopsNotSorted);//"TrainStopsNotSorted", "Train Stops is not sorted"
//            }
//        }
//    }
//
    //-------------------------------------------------------------------------------all
    public void createTrainCheck(CreateTrainRequest request) throws CheckErrorException {
        checkTrainNoAvailable(request.getTrainNo());
        multipleTrainCheck(request);
        Train.checkTrainStopsSorted(request);
    }

    //-------------------------------------------------------------------------------method
//    private Map<String, String> setErrorMessage(ErrorInfo errorInfo) {
//        Map<String, String> errorMessage = new HashMap<>();
//        errorMessage.put("code", errorInfo.getCode());
//        errorMessage.put("message", errorInfo.getErrorMessage());
//        return errorMessage;
//    }
//
//    private void throwCheckTrainException(ErrorInfo errorInfo) throws CheckErrorException {
//        List<Map<String, String>> errorList = new ArrayList<>();
//        Map<String, String> errorMessage = setErrorMessage(errorInfo);
//        errorList.add(errorMessage);
//        throw new CheckErrorException(errorList);
//    }
}

//    private Map<String, String> setErrorMessage(String code, String message) {
//        Map<String, String> errorMessage = new HashMap<>();
//        errorMessage.put("code", code);
//        errorMessage.put("message", message);
//        return errorMessage;
//    }

//    private void throwCheckTrainException(String code, String message) throws CheckErrorException {
//        List<Map<String, String>> errorList = new ArrayList<>();
//        Map<String, String> errorMessage = setErrorMessage(code, message);
//        errorList.add(errorMessage);
//        throw new CheckErrorException(errorList);
//    }
