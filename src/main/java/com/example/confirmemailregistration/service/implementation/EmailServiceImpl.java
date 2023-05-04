package com.example.confirmemailregistration.service.implementation;

import com.example.confirmemailregistration.config.EmailSenderSettings;
import com.example.confirmemailregistration.security.controller.request.Email;
import com.example.confirmemailregistration.service.EmailService;
import com.example.confirmemailregistration.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailSenderSettings emailSenderSettings;

    @Override
    public void sendEmail(Email email) {
        final String fromEmail = emailSenderSettings.getUsername();
        final String password = emailSenderSettings.getPassword();
        final String toEmail = email.getRecipient();

        Properties props = new Properties();
        props.put("mail.smtp.host", emailSenderSettings.getHost());
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, fromEmail, toEmail, email.getSubject(), email.getBody());

    }
}
