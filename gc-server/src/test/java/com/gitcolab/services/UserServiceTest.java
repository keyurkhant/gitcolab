package com.gitcolab.services;

import com.gitcolab.dto.*;
import com.gitcolab.dto.inhouse.request.*;
import com.gitcolab.dto.inhouse.response.JwtTokenResponse;
import com.gitcolab.dto.inhouse.response.MessageResponse;
import com.gitcolab.dto.inhouse.response.TokenRefreshResponse;
import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.UserRepository;
import com.gitcolab.configurations.jwt.JwtUtils;
import com.gitcolab.utilities.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private EmailSender emailSender;

    private UserService userService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(authenticationManager, userRepository, encoder, jwtUtils, refreshTokenService, emailSender);

    }

    @Test
    public void testAuthenticateUser_WhenAuthenticationSucceeds_ShouldReturnOkResponseWithJwtToken() {
        String username = "testuser";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest(username, password);
        String jwtToken = "testJwtToken";
        String refreshToken = "testRefreshToken";
        Long userId = 1L;
        String email = "test@example.com";

        Authentication authenticationMock = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(userId, username, email, password, null);
        RefreshToken createdRefreshToken = new RefreshToken(userId, null, refreshToken, Instant.now());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(userDetails)).thenReturn(jwtToken);
        when(refreshTokenService.createRefreshToken(userId)).thenReturn(createdRefreshToken);

        ResponseEntity<?> response = userService.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtTokenResponse responseBody = (JwtTokenResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals(jwtToken, responseBody.getToken());
        assertEquals(refreshToken, responseBody.getRefreshToken());
        assertEquals(userId, responseBody.getId());
        assertEquals(username, responseBody.getUsername());
        assertEquals(email, responseBody.getEmail());
        verify(authenticationManager).authenticate(any(Authentication.class));
        verify(refreshTokenService).createRefreshToken(userId);
    }

    @Test
    public void testRefreshToken() {
        // Set up mock behavior
        TokenRefreshRequest request = new TokenRefreshRequest("refresh-token");
        RefreshToken refreshToken = mock(RefreshToken.class);
        User user = mock(User.class);
        String newJwtToken = "new-jwt-token";
        TokenRefreshResponse expectedResponse = new TokenRefreshResponse(newJwtToken, request.getRefreshToken());

        when(refreshTokenService.findByToken(request.getRefreshToken())).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
        when(refreshToken.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("username");
        when(jwtUtils.generateTokenFromUsername("username")).thenReturn(newJwtToken);
        
        ResponseEntity<?> response = userService.refreshtoken(request);
        
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testAuthenticateUser_WhenAuthenticationSucceeds_ShouldSetAuthenticationAndSecurityContextHolder() {
        String username = "testuser";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest(username, password);
        String jwtToken = "testJwtToken";
        String refreshToken = "testRefreshToken";
        Long userId = 1L;
        String email = "test@example.com";

        Authentication authenticationMock = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(userId, username, email, password, null);
        RefreshToken createdRefreshToken = new RefreshToken(userId, null, refreshToken, Instant.now());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(userDetails)).thenReturn(jwtToken);
        when(refreshTokenService.createRefreshToken(userId)).thenReturn(createdRefreshToken);

        ResponseEntity<?> response = userService.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtTokenResponse responseBody = (JwtTokenResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals(jwtToken, responseBody.getToken());
        assertEquals(refreshToken, responseBody.getRefreshToken());
        assertEquals(userId, responseBody.getId());
        assertEquals(username, responseBody.getUsername());
        assertEquals(email, responseBody.getEmail());
        verify(authenticationManager).authenticate(any(Authentication.class));
        verify(refreshTokenService).createRefreshToken(userId);

        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(authenticationMock, authenticated);
        assertEquals(userDetails, authenticated.getPrincipal());
    }
    @Test
    public void testRegisterUser() {
        // Set up mock behavior
        RegisterUserRequest registerUserRequest = new RegisterUserRequest("username", "email", "password", "uFname", "uLname");
        User user = mock(User.class);
        MessageResponse expectedResponse = new MessageResponse("User registered successfully!");

        when(userRepository.existsByUsername(registerUserRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerUserRequest.getEmail())).thenReturn(false);
        when(encoder.encode(registerUserRequest.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(1);
        
        ResponseEntity<?> response = userService.registerUser(registerUserRequest);
        
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testRegisterUser_WhenUsernameIsAlreadyTaken_ShouldReturnBadRequestResponseWithErrorMessage() {
        String username = "existinguser";
        String email = "test@example.com";
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(username, email, "password", "John", "Doe");

        when(userRepository.existsByUsername(username)).thenReturn(true);

        ResponseEntity<?> response = userService.registerUser(registerUserRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Username is already taken!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegisterUser_WhenEmailIsAlreadyInUse_ShouldReturnBadRequestResponseWithErrorMessage() {
        String username = "testuser";
        String email = "existing@example.com";
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(username, email, "password", "John", "Doe");

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(email)).thenReturn(true);

        ResponseEntity<?> response = userService.registerUser(registerUserRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Email is already in use!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testResetPassword() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("abc@email.com", "ABC@123", "ABC@123");
        User user = mock(User.class);

        when(userRepository.existsByEmail(resetPasswordRequest.getEmail())).thenReturn(false);
        when(userRepository.getUserByEmail(resetPasswordRequest.getEmail())).thenReturn(Optional.ofNullable(user));

        ResponseEntity<?> response1 = userService.resetPassword(resetPasswordRequest);
        assertEquals(new MessageResponse("Error: User is not exist in system!"), response1.getBody());

    }
    @Test
    public void testResetPassword_WhenUserExistsAndPasswordsMatch_ShouldReturnOkResponse() {
        String email = "test@example.com";
        String password = "newPassword";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(email, password, password);
        User user = new User("username", email, "oldPassword", "firstname", "lastname");

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));
        when(encoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.update(user)).thenReturn(1);

        ResponseEntity<?> response = userService.resetPassword(resetPasswordRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password reset successfully!", ((MessageResponse) response.getBody()).getMessage());
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository).update(user);
    }

    @Test
    public void testResetPassword_WhenUserDoesNotExist_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "nonexistent@example.com";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(email, "password", "password");

        when(userRepository.existsByEmail(email)).thenReturn(false);

        ResponseEntity<?> response = userService.resetPassword(resetPasswordRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: User is not exist in system!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).getUserByEmail(email);
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    public void testResetPassword_WhenUserExistsButPasswordsDoNotMatch_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "test@example.com";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(email, "password1", "password2");
        User user = new User("username", email, "oldPassword", "firstname", "lastname");

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.resetPassword(resetPasswordRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Password and Confirm Password are not same!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    public void testResetPassword_WhenSomethingWentWrong_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "test@example.com";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(email, "password", "password");

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.resetPassword(resetPasswordRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Something went wrong!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    public void testValidateResetPasswordOTP_WhenUserExistsAndValidOTP_ShouldReturnOkResponse() {
        String email = "test@example.com";
        String otp = "123456";
        User user = new User("username", email, "oldPassword", "firstname", "lastname");
        user.setOtp(otp);
        user.setOtpExpiry(String.valueOf(Instant.now().plusSeconds(60).getEpochSecond()));
        ValidateOTPRequest validateOTPRequest = new ValidateOTPRequest(email, otp);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));
        when(userRepository.update(user)).thenReturn(1);

        ResponseEntity<?> response = userService.validateResetPasswordOTP(validateOTPRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OTP verified successfully!", ((MessageResponse) response.getBody()).getMessage());
        assertNull(user.getOtp());
        assertNull(user.getOtpExpiry());
        verify(userRepository).update(user);
    }

    @Test
    public void testValidateResetPasswordOTP_WhenUserDoesNotExist_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "nonexistent@example.com";
        String otp = "123456";
        ValidateOTPRequest validateOTPRequest = new ValidateOTPRequest(email, otp);

        when(userRepository.existsByEmail(email)).thenReturn(false);

        ResponseEntity<?> response = userService.validateResetPasswordOTP(validateOTPRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: User is not exist in system!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).getUserByEmail(email);
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    public void testValidateResetPasswordOTP_WhenUserExistsButOTPIsExpired_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "test@example.com";
        String otp = "123ABC";
        User user = new User("username", email, "oldPassword", "firstname", "lastname", otp, "16000000");
        user.setOtp(otp);
        user.setOtpExpiry(String.valueOf(Instant.now().minusSeconds(60).getEpochSecond()));
        ValidateOTPRequest validateOTPRequest = new ValidateOTPRequest(email, otp);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.validateResetPasswordOTP(validateOTPRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: OTP expired! Please resend.", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    public void testValidateResetPasswordOTP_WhenUserExistsButOTPIsInvalid_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "test@example.com";
        String validOTP = "123456";
        String invalidOTP = "654321";
        User user = new User("username", email, "oldPassword", "firstname", "lastname", invalidOTP, "16000000");
        user.setOtp(validOTP);
        user.setOtpExpiry(String.valueOf(Instant.now().plusSeconds(60).getEpochSecond()));
        ValidateOTPRequest validateOTPRequest = new ValidateOTPRequest(email, invalidOTP);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.validateResetPasswordOTP(validateOTPRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Invalid OTP! Please try again", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    public void testValidateResetPasswordOTP_WhenSomethingWentWrong_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "test@example.com";
        String otp = "123456";
        ValidateOTPRequest validateOTPRequest = new ValidateOTPRequest(email, otp);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.validateResetPasswordOTP(validateOTPRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Something went wrong!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    public void testSendResetPasswordOTP_WhenUserExistsAndEmailSentSuccessfully_ShouldReturnOkResponse() {
        String email = "test@example.com";
        User user = new User("username", email, "oldPassword", "firstname", "lastname", "123ABC", "16000000");
        SendOTPRequest sendOTPRequest = new SendOTPRequest(email);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));
        when(emailSender.sendEmail(anyString(), anyString(), anyString())).thenReturn(true);
        when(userRepository.update(user)).thenReturn(1);

        ResponseEntity<?> response = userService.sendResetPasswordOTP(sendOTPRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Verification Code sent successfully!", ((MessageResponse) response.getBody()).getMessage());
        assertNotNull(user.getOtp());
        assertNotNull(user.getOtpExpiry());
        verify(emailSender).sendEmail(eq(email), anyString(), anyString());
        verify(userRepository).update(user);
    }

    @Test
    public void testSendResetPasswordOTP_WhenUserDoesNotExist_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "nonexistent@example.com";
        SendOTPRequest sendOTPRequest = new SendOTPRequest(email);

        when(userRepository.existsByEmail(email)).thenReturn(false);

        ResponseEntity<?> response = userService.sendResetPasswordOTP(sendOTPRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: User is not exist in system!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).getUserByEmail(email);
        verify(userRepository, never()).update(any(User.class));
        verify(emailSender, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    public void testSendResetPasswordOTP_WhenSomethingWentWrongDuringEmailSending_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "test@example.com";
        User user = new User("username", email, "oldPassword", "firstname", "lastname", "123ABC", "16000000");
        SendOTPRequest sendOTPRequest = new SendOTPRequest(email);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));
        when(emailSender.sendEmail(anyString(), anyString(), anyString())).thenReturn(false);

        ResponseEntity<?> response = userService.sendResetPasswordOTP(sendOTPRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Something went wrong!", ((MessageResponse) response.getBody()).getMessage());
        assertNotNull(user.getOtp());
        assertNotNull(user.getOtpExpiry());
        verify(emailSender).sendEmail(eq(email), anyString(), anyString());
    }

    @Test
    public void testSendResetPasswordOTP_WhenSomethingWentWrong_ShouldReturnBadRequestResponseWithErrorMessage() {
        String email = "test@example.com";
        SendOTPRequest sendOTPRequest = new SendOTPRequest(email);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.sendResetPasswordOTP(sendOTPRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Something went wrong!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).update(any(User.class));
        verify(emailSender, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    public void testUpdateProfile_WhenWrong_ShouldReturnErrorMessage() {
        //Arrange
        String username = "Uchenna";
        String organization = "dalhousie";
        String description = "fakeDesc";
        String location = "halifax";
        String resume = "resume";
        String github = "github";
        String linkedin = "linkedin";
        String profilePicture = "profilePicture";

        UpdateUserProfileRequest updateUserProfileRequest = new UpdateUserProfileRequest(username, organization, location,
                                                            description , linkedin, github, resume, profilePicture);


        //Act
        when(userRepository.existsByUsername(username)).thenReturn(true);

        ResponseEntity<?> response = userService.updateUserProfile(updateUserProfileRequest);

        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Something went wrong with updating profile", ((MessageResponse) response.getBody()).getMessage());

        //Verify
        verify(userRepository, times(1)).findByUsername(anyString());

    }


    @Test
    void testGetUserByUsername_ValidUsernameExists_ReturnUserDTO() {
        String validUsername = "john_doe";
        User user = new User(validUsername, "john@example.com", "password", "John", "Doe");
        when(userRepository.findByUsername(validUsername)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.getUserByUsername(validUsername);

        assertTrue(response.getBody() instanceof UserDTO);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetUserByUsername_InvalidUsername_ReturnBadRequest() {
        String invalidUsername = null;
        ResponseEntity<?> response = userService.getUserByUsername(invalidUsername);

        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetUserByUsername_ValidUsernameNotExists_ReturnBadRequest() {
        String nonExistingUsername = "non_existing_user";
        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.getUserByUsername(nonExistingUsername);

        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetUser_ValidUsernameExists_ReturnUserDTO() {
        String validUsername = "john_doe";
        User user = new User(validUsername, "john@example.com", "password", "John", "Doe");
        when(userRepository.findByUsername(validUsername)).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.getUser(validUsername);

        assertNotNull(userDTO);
    }

    @Test
    void testGetUser_InvalidUsername_ReturnNull() {
        String invalidUsername = null;

        UserDTO userDTO = userService.getUser(invalidUsername);

        assertNull(userDTO);
    }

    @Test
    void testGetUser_ValidUsernameNotExists_ReturnNull() {
        String nonExistingUsername = "non_existing_user";
        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        UserDTO userDTO = userService.getUser(nonExistingUsername);

        assertNull(userDTO);
    }

    @Test
    void testUpdateUserProfile_ValidRequest_UpdateSuccessful() {
        String validUsername = "john_doe";
        User user = new User(validUsername, "john@example.com", "password", "John", "Doe");
        UpdateUserProfileRequest request = new UpdateUserProfileRequest(validUsername, "Company", "Location", "Description", "LinkedIn", "GitHub", "Resume", "ProfilePic");
        when(userRepository.findByUsername(validUsername)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.updateUserProfile(request);

        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateUserProfile_NonExistingUser_ReturnBadRequest() {
        String nonExistingUsername = "non_existing_user";
        UpdateUserProfileRequest request = new UpdateUserProfileRequest(nonExistingUsername, "Company", "Location", "Description", "LinkedIn", "GitHub", "Resume", "ProfilePic");
        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.updateUserProfile(request);

        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testUpdateUserProfile_InvalidRequest_ReturnBadRequest() {
        UpdateUserProfileRequest invalidRequest = new UpdateUserProfileRequest(null, null, null, null, null, null, null, null);

        ResponseEntity<?> response = userService.updateUserProfile(invalidRequest);

        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals(400, response.getStatusCodeValue());
    }

}
