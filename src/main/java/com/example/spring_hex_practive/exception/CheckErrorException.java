package com.example.spring_hex_practive.exception;

import lombok.*;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
public class CheckErrorException extends Exception {
    private List<Map<String,String>> errorList;
    public CheckErrorException(List<Map<String,String>> errorList) {
        this.errorList = errorList;
    }

}
