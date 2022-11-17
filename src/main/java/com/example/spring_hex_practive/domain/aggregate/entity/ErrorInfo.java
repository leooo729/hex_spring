package com.example.spring_hex_practive.domain.aggregate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ErrorInfo {
    TrainNoNotExists("TrainNoNotExists", "Train No does not exists"),
    TicketStopsInvalid("TicketStopsInvalid", "Ticket From & To is invalid"),
    StopsNameError("StopsNameError", "Stop Name is Not Exist"),
    TrainNotAvailable("TrainNotAvailable", "Train is not available"),
    TrainNoExists("TrainNoExists", "Train No is exists"),
    TrainKindInvalid("TrainKindInvalid", "Train Kind is invalid"),
    TrainStopsDuplicate("TrainStopsDuplicate", "Train Stops is duplicate"),
    TrainStopNameNoExists("TrainStopNameNoExists", "Train Stop Name is not exists"),
    TrainStopsNotSorted("TrainStopsNotSorted", "Train Stops is not sorted"),
    TrainNoNotExistsCN("TrainNoNotExistsCN","車次不存在"),
    TrainNameNotExistsCN("TrainNameNotExistsCN","站名不存在")

    ;


    private String code;
    private String errorMessage;

    ErrorInfo(String code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

}
