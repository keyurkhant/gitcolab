package com.gitcolab.dto.inhouse.request;

import com.gitcolab.dto.inhouse.request.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

class LoginRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testBlankUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("password");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        Assertions.assertEquals(1, violations.size());

        ConstraintViolation<LoginRequest> violation = violations.iterator().next();
        Assertions.assertEquals("must not be blank", violation.getMessage());
        Assertions.assertEquals("username", violation.getPropertyPath().toString());
    }

    @Test
    void testBlankPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        Assertions.assertEquals(1, violations.size());

        ConstraintViolation<LoginRequest> violation = violations.iterator().next();
        Assertions.assertEquals("must not be blank", violation.getMessage());
        Assertions.assertEquals("password", violation.getPropertyPath().toString());
    }
}
