package com.example.spring_hex_practive.controller.dto.response;

import com.example.spring_hex_practive.exception.CheckErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckErrorResponse {
    private String error;
    private List<Map<String, String>> checkErrors;
    public CheckErrorResponse(CheckErrorException e) {
        this.error = "VALIDATE_FAILED";
        this.checkErrors = e.getErrorList();
    }
}
