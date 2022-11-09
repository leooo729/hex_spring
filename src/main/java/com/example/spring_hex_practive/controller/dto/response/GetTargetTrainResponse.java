package com.example.spring_hex_practive.controller.dto.response;

import com.example.spring_hex_practive.controller.dto.request.Stops;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTargetTrainResponse {
    @JsonProperty(value = "train_no")
    private Integer trainNo;
    @JsonProperty(value = "train_kind")
    private String trainKind;
    private List<Stops> stops;
}
