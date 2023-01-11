package org.gab.ClouDuck.controllers.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.gab.ClouDuck.controllers.exceptions.annotations.BasicResponseExceptionHandler;
import org.gab.ClouDuck.exceptions.UserNotFoundException;
import org.gab.ClouDuck.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.GeneralSecurityException;
import java.util.NoSuchElementException;

@ControllerAdvice(annotations = BasicResponseExceptionHandler.class)
@Slf4j
public class AWSAccountAdvice {

    @ExceptionHandler(value = {NoSuchElementException.class, NullPointerException.class})
    public ResponseEntity<Response> nullHandler(NoSuchElementException e) {

        log.error("Account operation exception", e);

        return new ResponseEntity<>(Response.error(new UserNotFoundException(e)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Response> otherHandler(Exception e) {

        log.error("Account operation exception", e);

        var response = Response.error(e);
        response.concatToMessage("Operation with account error");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {GeneralSecurityException.class})
    public ResponseEntity<Response> cryptoHandler(GeneralSecurityException e) {

        log.error("Crypto operation exception", e);

        var response = Response.error(e);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
