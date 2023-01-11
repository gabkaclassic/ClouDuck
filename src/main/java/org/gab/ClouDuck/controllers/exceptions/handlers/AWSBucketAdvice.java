package org.gab.ClouDuck.controllers.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.gab.ClouDuck.controllers.exceptions.annotations.BucketResponseExceptionHandler;
import org.gab.ClouDuck.responses.BucketResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice(annotations = BucketResponseExceptionHandler.class)
@Slf4j
public class AWSBucketAdvice {

    @ExceptionHandler(value = {NoSuchElementException.class, NullPointerException.class})
    public ResponseEntity<BucketResponse> nullHandler(Exception e) {

        log.error("Bucket operation exception", e);

        var response = BucketResponse.error(e);
        response.concatToMessage("Bucket or its content was not found");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<BucketResponse> otherHandler(Exception e) {

        log.error("Bucket operation exception", e);

        var response = BucketResponse.error(e);
        response.concatToMessage("Operation with bucket error");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
