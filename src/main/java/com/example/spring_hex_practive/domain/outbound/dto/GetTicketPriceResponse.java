package com.example.spring_hex_practive.domain.outbound.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTicketPriceResponse {
    private Double sold;
    private Double string;
    private Double pending;
    private Double available;
    @JsonProperty(value = "not_available")
    private Double notAvailable;
    @JsonProperty(value = "connector_up")
    private Double connectorUp;
    private Double lqmwWVoQF;
    private Double nglHMqXkEm;
    private Double GptgZaRM;

}
