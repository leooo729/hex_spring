package com.example.spring_hex_practive.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainInfo {
    @JsonProperty(value = "train_no")
    private Integer trainNo;
    @JsonProperty(value = "train_kind")
    private String trainKind;
}
