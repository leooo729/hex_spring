package com.example.spring_hex_practive.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stops {
    @JsonProperty(value = "stop_name")
    private String stopName;
    @Pattern(regexp = "(^([0-1]?\\d|2[0-3]):([0-5]?\\d)$)",message = "時間格式錯誤")
    @JsonProperty(value = "stop_time")
    private String stopTime;

}
