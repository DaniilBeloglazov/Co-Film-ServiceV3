package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.dto.MessageResponse;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<MessageResponse> handleInvalidRequests(BindException exception) {

        val message = new MessageResponse(exception.getMessage());

        if (message.getMessage().contains("Multipart"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Multipart file should be not blank. Uncheck send empty value."));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
