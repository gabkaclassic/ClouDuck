package org.gab.ClouDuck.controllers.exceptions.handlers;


import lombok.extern.slf4j.Slf4j;
import org.gab.ClouDuck.controllers.exceptions.annotations.ObjectResponseExceptionHandler;
import org.gab.ClouDuck.responses.ObjectResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice(annotations = ObjectResponseExceptionHandler.class)
@Slf4j
public class AWSObjectAdvice {

    @ExceptionHandler(value = {IOException.class})
    public ObjectResponse IOHandler(IOException e) {

        log.error("Object operation exception", e);

        var response = ObjectResponse.error(e);
        response.concatToMessage("Processing of file content error");

        return response;
    }
    @ExceptionHandler(value = {NoSuchElementException.class, NullPointerException.class})
    public ObjectResponse nullHandler(Exception e) {

        log.error("Object operation exception", e);

        var response = ObjectResponse.error(e);
        response.concatToMessage("File or its content was not found");

        return response;
    }

    @ExceptionHandler(value = {Exception.class})
    public ObjectResponse otherHandler(Exception e) {

        log.error("Object operation exception", e);

        var response = ObjectResponse.error(e);
        response.concatToMessage("Operation with object error");

        return response;
    }
}
