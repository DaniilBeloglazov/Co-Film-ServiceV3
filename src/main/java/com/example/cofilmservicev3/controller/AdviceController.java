package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.dto.MessageResponse;
import com.example.cofilmservicev3.exception.FilmAlreadyExsitsException;
import com.example.cofilmservicev3.exception.FilmNotFoundException;
import com.example.cofilmservicev3.exception.PersonAlreadyExistsException;
import com.example.cofilmservicev3.exception.PersonNotFoundException;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

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

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<MessageResponse> handleMultipartNotPresent(MissingServletRequestPartException exception) {

        val message = new MessageResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(value = PersonNotFoundException.class)
    public ResponseEntity<MessageResponse> handleNotFoundPerson(PersonNotFoundException exception) {

        val message = new MessageResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(value = FilmNotFoundException.class)
    public ResponseEntity<MessageResponse> handleNotFoundFilm(FilmNotFoundException exception) {

        val message = new MessageResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<MessageResponse> handlePersonAlreadyExists(PersonAlreadyExistsException exception) {

        val message = new MessageResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(FilmAlreadyExsitsException.class)
    public ResponseEntity<MessageResponse> handleFilmAlreadyExists(FilmAlreadyExsitsException exception) {

        val message = new MessageResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MessageResponse> handleInvalidUriParam(MethodArgumentTypeMismatchException exception) {

        val message = new MessageResponse("Invalid URI. " + exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
