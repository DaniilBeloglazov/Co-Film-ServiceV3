package com.example.cofilmservicev3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private String field;

    private Object rejectedValue;

    private String message;


    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
