package com.gitcolab.utilities;


import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailSenderTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSender emailSender;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail_Successful() {
        String emailTo = "example@example.com";
        String subject = "Test Subject";
        String message = "Test Message";

        Mockito.doNothing().when(javaMailSender).send(Mockito.any(SimpleMailMessage.class));

        boolean result = emailSender.sendEmail(emailTo, subject, message);

        Assertions.assertTrue(result);
    }

    @Test
    void testSendEmail_Failure() {
        String emailTo = "example@example.com";
        String subject = "Test Subject";
        String message = "Test Message";

        Mockito.doThrow(new RuntimeException("Failed to send email")).when(javaMailSender).send(Mockito.any(SimpleMailMessage.class));

        boolean result = emailSender.sendEmail(emailTo, subject, message);
        Assertions.assertFalse(result);
    }

}
