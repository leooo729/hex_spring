package com.example.spring_hex_practive.iface.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
