package com.example.spring_hex_practive.advice;

import com.example.spring_hex_practive.controller.dto.response.CheckErrorResponse;
import com.example.spring_hex_practive.controller.dto.response.FieldErrorResponse;
import com.example.spring_hex_practive.exception.DataNotFoundException;
import com.example.spring_hex_practive.exception.CheckErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {
    //    MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldErrorResponse> handler(MethodArgumentNotValidException e) {

        FieldErrorResponse error = new FieldErrorResponse(e);

        return new ResponseEntity<FieldErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

    //ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FieldErrorResponse> handler(ConstraintViolationException e) {

        FieldErrorResponse error = new FieldErrorResponse(e);

        return new ResponseEntity<FieldErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map> handler(DataNotFoundException e) {

        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("error", e.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CheckErrorException.class)
    public ResponseEntity<CheckErrorResponse> handler(CheckErrorException e) {

        CheckErrorResponse error = new CheckErrorResponse(e);

        return new ResponseEntity<CheckErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<FieldErrorResponse> handler(NumberFormatException e) {

        FieldErrorResponse error = new FieldErrorResponse(e);

        return new ResponseEntity<FieldErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
}
