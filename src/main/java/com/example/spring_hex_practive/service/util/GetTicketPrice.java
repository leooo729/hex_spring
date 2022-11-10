package com.example.spring_hex_practive.service.util;

import com.example.spring_hex_practive.service.outboundApiDto.GetTicketPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GetTicketPrice {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${ticketPriceURL}")
    String ticketPriceUrl;

    public Double getCurrentPrice() {
        ResponseEntity<GetTicketPriceResponse> responseEntity = restTemplate.getForEntity(ticketPriceUrl, GetTicketPriceResponse.class);
        return responseEntity.getBody().getString();
    }
}
