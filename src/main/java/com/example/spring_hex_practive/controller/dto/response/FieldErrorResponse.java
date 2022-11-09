package com.example.spring_hex_practive.controller.dto.response;

import lombok.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorResponse {
    private String error;
    private List<Map<String, String>> fieldError;

    public FieldErrorResponse(NumberFormatException e) {
        this.error = "Validate Failed";
        this.fieldError = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        map.put("code", "Min");
        map.put("message", "車次必須為正整數");
        map.put("field", "trainNo");

        fieldError.add(map);
    }


    public FieldErrorResponse(ConstraintViolationException e) {
        this.error = "Validate Failed";

        this.fieldError = new ArrayList<>();

        e.getConstraintViolations().stream().forEach(c -> {

            String fieldName = "";
            //LeafNode //nodeList
            for (Path.Node node : c.getPropertyPath()) {
                fieldName = node.getName();
            }

            Map<String, String> map = new HashMap<>();

            String code = c.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            if (!"NotEmpty".equals(code)) {
                map.put("code", code);
            }
            map.put("message", c.getMessage());
            map.put("field", fieldName);

            fieldError.add(map);
        });
    }

    public FieldErrorResponse(MethodArgumentNotValidException e) {
        this.error = "Validate Failed";
        this.fieldError = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(m -> {
            Map<String, String> fieldMap = new HashMap<>();

            fieldMap.put("fields", m.getField());
            fieldMap.put("code", m.getCode());
            fieldMap.put("message ", m.getDefaultMessage());

            fieldError.add(fieldMap);
        });
    }
}
