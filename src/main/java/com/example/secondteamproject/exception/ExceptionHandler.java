package com.example.secondteamproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleException(IllegalArgumentException e) {
        RestApiException restApiException = new RestApiException();
        restApiException.setErrorCode("400");
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(e.getMessage());
        return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
        RestApiException restApiException = new RestApiException();
        restApiException.setErrorCode("400");
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(e.getFieldError().getDefaultMessage());
        return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
    }


}
