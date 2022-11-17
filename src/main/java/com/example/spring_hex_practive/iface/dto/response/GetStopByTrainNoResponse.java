package com.example.spring_hex_practive.iface.dto.response;

import com.example.spring_hex_practive.iface.dto.request.Stops;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"train_no", "train_kind", "stops"})
public class GetStopByTrainNoResponse {
    @JsonProperty(value = "train_no")
    private Integer trainNo;
    @JsonProperty(value = "train_kind")
    private String trainKind;
    private List<Stops> stops;
}
