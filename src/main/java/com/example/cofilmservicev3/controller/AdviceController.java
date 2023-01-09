package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.dto.MessageResponse;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    //TODO
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageResponse> handleAll(RuntimeException runtimeException) {

        val message = new MessageResponse(runtimeException.getMessage() + ". Ili chto to ne tak na samom dele");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
