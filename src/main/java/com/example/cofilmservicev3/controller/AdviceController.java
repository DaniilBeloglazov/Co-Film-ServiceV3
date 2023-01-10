package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.dto.MessageResponse;
import lombok.val;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@RestControllerAdvice
public class AdviceController {

    //TODO
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<MessageResponse> handleAll(RuntimeException runtimeException) {
//
//        val message = new MessageResponse(runtimeException.getMessage());
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
//    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<MessageResponse> handleInvalidRequests(BindException exception) {

        val message = new MessageResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
