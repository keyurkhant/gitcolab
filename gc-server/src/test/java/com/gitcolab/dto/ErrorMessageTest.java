package com.gitcolab.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Date;

class ErrorMessageTest {

    @Test
    void testConstructorAndGetters() {
        int statusCode = 404;
        Date timestamp = new Date();
        String message = "Not Found";
        String description = "The requested resource was not found.";

        ErrorMessage errorMessage = new ErrorMessage(statusCode, timestamp, message, description);

        Assertions.assertEquals(statusCode, errorMessage.getStatusCode());
        Assertions.assertEquals(timestamp, errorMessage.getTimestamp());
        Assertions.assertEquals(message, errorMessage.getMessage());
        Assertions.assertEquals(description, errorMessage.getDescription());
    }

    @Test
    void testSetters() {
        ErrorMessage errorMessage = new ErrorMessage();

        int statusCode = 500;
        Date timestamp = new Date();
        String message = "Internal Server Error";
        String description = "An unexpected error occurred.";

        errorMessage.setStatusCode(statusCode);
        errorMessage.setTimestamp(timestamp);
        errorMessage.setMessage(message);
        errorMessage.setDescription(description);

        Assertions.assertEquals(statusCode, errorMessage.getStatusCode());
        Assertions.assertEquals(timestamp, errorMessage.getTimestamp());
        Assertions.assertEquals(message, errorMessage.getMessage());
        Assertions.assertEquals(description, errorMessage.getDescription());
    }

    @Test
    void testToString() {
        int statusCode = 404;
        Date timestamp = new Date();
        String message = "Not Found";
        String description = "The requested resource was not found.";

        ErrorMessage errorMessage = new ErrorMessage(statusCode, timestamp, message, description);

        String expectedToString = "ErrorMessage(statusCode=404, timestamp=" + timestamp.toString() + ", message=Not Found, description=The requested resource was not found.)";
        Assertions.assertEquals(expectedToString, errorMessage.toString());
    }
}