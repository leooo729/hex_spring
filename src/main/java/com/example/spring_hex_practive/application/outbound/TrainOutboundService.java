package com.example.spring_hex_practive.application.outbound;

import com.example.spring_hex_practive.domain.outbound.dto.CheckResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TrainOutboundService {
    @Value("${checkTrainAvailableURL}")
    String checkTrainAvailableUrl;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<CheckResponse> checkTrainNoAvailable(Integer trainNo) {

        String url = checkTrainAvailableUrl + trainNo;

        ResponseEntity<CheckResponse> responseEntity = restTemplate.getForEntity(url, CheckResponse.class);
        return responseEntity;
    }
}
