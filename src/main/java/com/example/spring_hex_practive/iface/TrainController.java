package com.example.spring_hex_practive.iface;

import com.example.spring_hex_practive.iface.dto.request.BuyTicketRequest;
import com.example.spring_hex_practive.iface.dto.request.CreateTrainRequest;
import com.example.spring_hex_practive.iface.dto.response.*;
//import com.example.spring_hex_practive.exception.DataNotFoundException;
import com.example.spring_hex_practive.exception.DataNotFoundException;
import com.example.spring_hex_practive.exception.CheckErrorException;
import com.example.spring_hex_practive.application.commandService.TicketCommandService;
import com.example.spring_hex_practive.application.commandService.TrainCommandService;
import com.example.spring_hex_practive.application.queryService.TrainQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class TrainController {
    @Autowired
    private TicketCommandService ticketCommandService;
    @Autowired
    private TrainCommandService trainCommandService;
    @Autowired
//    @Qualifier("queryService")
    private TrainQueryService trainQueryService;

    @GetMapping("/train/{trainNo}/stops")
    public GetStopByTrainNoResponse getTargetTrain(@PathVariable @Min(value = 0, message = "車次必須為正整數") Integer trainNo) throws DataNotFoundException {
        GetStopByTrainNoResponse response = trainQueryService.getStopByTrainNo(trainNo);
        return response;
    }

    @GetMapping("/train")
    public List<TrainInfo> getTrainByStop(@RequestParam @NotEmpty(message = "Required String parameter 'via' is not present") String via) throws DataNotFoundException {
        List<TrainInfo> response = trainQueryService.getTrainByStop(via);
        return response;
    }

    @PostMapping("/train")
    public ResponseEntity<Map<String, String>> createTrainInfo(@RequestBody @Valid CreateTrainRequest request) throws CheckErrorException {
        Map response = stringToMapDisplay(trainCommandService.CreateTrainAndStop(request));
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
    }

    @PostMapping("/ticket")
    public ResponseEntity<Map<String, String>> buyTicket(@RequestBody @Valid BuyTicketRequest request) throws CheckErrorException {
        Map<String, String> response = stringToMapDisplay(ticketCommandService.buyTicket(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Map<String, String> stringToMapDisplay(String uuid) {
        Map<String, String> mapResponse = new HashMap<>();
        mapResponse.put("uuid", uuid);
        return mapResponse;
    }
}
