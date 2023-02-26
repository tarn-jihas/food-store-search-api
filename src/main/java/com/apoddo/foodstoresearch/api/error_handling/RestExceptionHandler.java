package com.apoddo.foodstoresearch.api.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;


/**
 * The RestExceptionHandler class handles exceptions thrown by RESTful endpoints and returns an appropriate HTTP response.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the IOException thrown by RESTful endpoints and returns an HTTP 500 error response.
     *
     * @param ex the IOException thrown by the endpoint
     * @return an HTTP 500 error response
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException ex) {
        String message = "An error occurred while reading from Elasticsearch.";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles the IllegalArgumentException thrown by RESTful endpoints and returns an HTTP 400 error response.
     *
     * @param ex the IllegalArgumentException thrown by the endpoint
     * @return an HTTP 400 error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "Invalid input parameter: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the exceptions thrown by RESTful endpoints and returns an HTTP 500 error response.
     *
     * @param ex the IllegalArgumentException thrown by the endpoint
     * @return an HTTP 400 error response
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        String message = "An error occurred while searching for food stores: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
