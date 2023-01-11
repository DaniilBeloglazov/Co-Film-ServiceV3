package com.example.cofilmservicev3.exception;

public class FilmAlreadyExsitsException extends RuntimeException {

    public FilmAlreadyExsitsException(String message) {
        super(message);
    }
}
