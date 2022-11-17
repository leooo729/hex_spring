package com.example.spring_hex_practive.exception;

import com.example.spring_hex_practive.domain.aggregate.domainService.ErrorInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckErrorException extends Exception {
    private List<ErrorInfo> errorList=new ArrayList<>();
    public CheckErrorException(ErrorInfo errorInfo) {
        this.errorList.add(errorInfo);
    }


}
