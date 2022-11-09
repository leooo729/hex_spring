package com.example.spring_hex_practive.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainRequest {
    @NotNull(message = "車次不可為空")
    @JsonProperty(value = "train_no")
    private Integer trainNo;
    @NotEmpty(message = "車種不可為空")
    @JsonProperty(value = "train_kind")
    private String trainKind;
    @NotEmpty(message = "停靠站不可為空")
    @Valid
    private List<Stops> stops;

}
