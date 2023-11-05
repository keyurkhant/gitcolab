package com.gitcolab.controllers;

import com.gitcolab.dto.inhouse.request.*;
import com.gitcolab.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        return userService.registerUser(registerUserRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
       return userService.refreshtoken(request);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendResetPasswordOTP(@Valid @RequestBody SendOTPRequest request) {
        return userService.sendResetPasswordOTP(request);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateResetPasswordOTP(@Valid @RequestBody ValidateOTPRequest request) {
        return userService.validateResetPasswordOTP(request);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return userService.resetPassword(request);
    }
}
