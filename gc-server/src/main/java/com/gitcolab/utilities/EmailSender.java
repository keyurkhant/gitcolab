package com.gitcolab.utilities;


import com.gitcolab.services.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    private static Logger logger = LoggerFactory.getLogger(EmailSender.class);
    @Autowired
    private JavaMailSender javaMailSender;

    EmailSender() {}

    public boolean sendEmail(String emailTo, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("noreply@gitcolab.com");
        msg.setTo(emailTo);
        msg.setSubject(subject);
        msg.setText(message);
        try {
            javaMailSender.send(msg);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
