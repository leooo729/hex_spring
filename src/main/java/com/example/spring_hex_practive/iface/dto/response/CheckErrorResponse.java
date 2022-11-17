package com.example.spring_hex_practive.iface.dto.response;

import com.example.spring_hex_practive.exception.CheckErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
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
        this.checkErrors = new ArrayList<>();
        e.getErrorList().forEach(errorInfo -> {
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("code", errorInfo.getCode());
            errorMessage.put("message", errorInfo.getErrorMessage());
            this.checkErrors.add(errorMessage);
        });
    }
}
