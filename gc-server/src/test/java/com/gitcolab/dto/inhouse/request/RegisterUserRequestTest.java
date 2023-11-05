package com.gitcolab.dto.inhouse.request;

import com.gitcolab.dto.inhouse.request.RegisterUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.*;
import java.util.Iterator;
import java.util.Set;

class RegisterUserRequestTest {

    private ValidatorFactory factory;
    private Validator validator;

    @BeforeEach
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRegisterUserRequest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("john123");
        request.setEmail("john@example.com");
//        request.setRole(new HashSet<>());
        request.setPassword("password123");
        request.setFirstName("Example");
        request.setLastName("User");

        Set<ConstraintViolation<RegisterUserRequest>> violations = validator.validate(request);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidUsername() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("12");
        request.setEmail("john@example.com");
//        request.setRole(new HashSet<>());
        request.setPassword("password123");
        request.setFirstName("Example");
        request.setLastName("User");

        Set<ConstraintViolation<RegisterUserRequest>> violations = validator.validate(request);
        Assertions.assertEquals(1, violations.size());
        Iterator iterator=violations.iterator();
        ConstraintViolation<RegisterUserRequest> violation = (ConstraintViolation<RegisterUserRequest>) iterator.next();
        Assertions.assertEquals("size must be between 3 and 20", violation.getMessage());
        Assertions.assertEquals("username", violation.getPropertyPath().toString());

    }

    @Test
    void testInvalidEmail() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("john123");
        request.setEmail("invalidemail");
//        request.setRole(new HashSet<>());
        request.setPassword("password123");
        request.setFirstName("Example");
        request.setLastName("User");

        Set<ConstraintViolation<RegisterUserRequest>> violations = validator.validate(request);
        Assertions.assertEquals(1, violations.size());

        ConstraintViolation<RegisterUserRequest> violation = violations.iterator().next();
        Assertions.assertEquals("must be a well-formed email address", violation.getMessage());
        Assertions.assertEquals("email", violation.getPropertyPath().toString());
    }

}
