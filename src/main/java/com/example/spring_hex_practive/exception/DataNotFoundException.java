package com.example.spring_hex_practive.exception;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class DataNotFoundException extends Exception {
    private String message;
    public DataNotFoundException(String message) {
        this.message = message;
    }
}
