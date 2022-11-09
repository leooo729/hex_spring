package com.example.spring_hex_practive.service;

import com.example.spring_hex_practive.controller.dto.response.GetTargetTrainResponse;
import com.example.spring_hex_practive.controller.dto.response.TrainInfo;
import com.example.spring_hex_practive.exception.DataNotFoundException;

import java.util.List;

public interface QueryService {
    GetTargetTrainResponse getTargetTrain(Integer trainNo) throws DataNotFoundException;

    List<TrainInfo> getTrainByStop(String stopName) throws DataNotFoundException;

}
