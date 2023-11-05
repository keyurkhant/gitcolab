package com.gitcolab.dto.inhouse.response;

import com.gitcolab.dto.inhouse.response.MessageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageResponseTest {

    @Test
    void testConstructorAndGetters() {
        String message = "Test Message";
        MessageResponse response = new MessageResponse(message);

        Assertions.assertEquals(message, response.getMessage());
    }

    @Test
    void testSetter() {
        String message = "Test Message";
        MessageResponse response = new MessageResponse();
        response.setMessage(message);

        Assertions.assertEquals(message, response.getMessage());
    }

    @Test
    void testToString() {
        String message = "Test Message";
        MessageResponse response = new MessageResponse(message);

        String expectedToString = "MessageResponse(message=Test Message)";
        Assertions.assertEquals(expectedToString, response.toString());
    }
}
