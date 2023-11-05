package com.gitcolab.dto.inhouse.request;

import com.gitcolab.dto.inhouse.request.TokenRefreshRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import jakarta.validation.*;

import java.util.Set;

class TokenRefreshRequestTest {

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    @Test
    void testValidTokenRefreshRequest() {
        TokenRefreshRequest request = new TokenRefreshRequest("validRefreshToken");

        Set<ConstraintViolation<TokenRefreshRequest>> violations = validator.validate(request);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidRefreshToken() {
        TokenRefreshRequest request = new TokenRefreshRequest("");

        Set<ConstraintViolation<TokenRefreshRequest>> violations = validator.validate(request);
        Assertions.assertEquals(1, violations.size());

        ConstraintViolation<TokenRefreshRequest> violation = violations.iterator().next();
        Assertions.assertEquals("must not be blank", violation.getMessage());
        Assertions.assertEquals("refreshToken", violation.getPropertyPath().toString());
    }

    // Add additional test cases if needed
}
