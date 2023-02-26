package com.apoddo.foodstoresearch.api;

import java.io.IOException;

import com.apoddo.foodstoresearch.api.error_handling.RestExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Test
    void testHandleIOException() {
        String message = "An error occurred while reading from Elasticsearch.";
        ResponseEntity<Object> response = restExceptionHandler.handleIOException(new IOException(message));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(response.getBody(), equalTo(message));
    }

    @Test
    void testHandleIllegalArgumentException() {
        String message = "Invalid input parameter: invalid";
        ResponseEntity<Object> response = restExceptionHandler.handleIllegalArgumentException(new IllegalArgumentException("invalid"));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(response.getBody(), equalTo(message));
    }

}