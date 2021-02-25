package com.neueda.test.neueda.controller;

import com.neueda.test.neueda.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@SuppressWarnings({"unchecked","rawtypes"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    public ResponseExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({ PinInvalidException.class })
    public ResponseEntity<Object> handlePinInvalidException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ AccountNumberNotFoundException.class })
    public ResponseEntity<Object> handleAccountNumberNotFoundException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ AtmMoneylessException.class })
    public ResponseEntity<Object> handleAtmMoneylessException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ MultiplesOfFiveException.class })
    public ResponseEntity<Object> handleMultipleOfFiveException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ AccountBalanceException.class })
    public ResponseEntity<Object> handleAccountBalanceException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ DispenseExactAmountException.class })
    public ResponseEntity<Object> handleDispenseExactAmountException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ InternalErrorException.class })
    public ResponseEntity<Object> handleInternalErrorException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                        HttpHeaders httpHeaders, HttpStatus status, WebRequest request) {

        return  new ResponseEntity<>(ex.getMessage(), httpHeaders, HttpStatus.BAD_REQUEST);
    }
}
