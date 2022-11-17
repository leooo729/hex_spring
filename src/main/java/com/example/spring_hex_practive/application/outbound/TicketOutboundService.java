package com.example.spring_hex_practive.application.outbound;

import com.example.spring_hex_practive.domain.outbound.dto.GetTicketPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TicketOutboundService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${ticketPriceURL}")
    String ticketPriceUrl;

    public Double getTicketPrice() {
        ResponseEntity<GetTicketPriceResponse> responseEntity = restTemplate.getForEntity(ticketPriceUrl, GetTicketPriceResponse.class);
        //檢核 //ioc
        return responseEntity.getBody().getString();
    }
}
