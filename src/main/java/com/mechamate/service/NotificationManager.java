package com.mechamate.service;

import com.mechamate.entity.UserProfile;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {

    @Autowired
    private JavaMailSender mailClient;

    @Value("${spring.mail.username}")
    private String sender;

    public boolean sendActivationEmail(UserProfile userProfile) {
        try {
            MimeMessage mimeMessage = mailClient.createMimeMessage();
            MimeMessageHelper email = new MimeMessageHelper(mimeMessage, "UTF-8");
            email.setSubject("MechaMate - Activate Your Account");
            email.setFrom(sender);
            email.setTo(userProfile.getEmail());
            email.setText("<html><h3>Hello " + userProfile.getFirstName() + ",<h3>\n\n<p>Please click following activation key in order to get your account activated</p>\n\n" +
                    "<a href=\"http://localhost:8080/api/v1/auth/activate?key=" + userProfile.getActivationKey() + "\">Activate My Account</a><html>", true);
            mailClient.send(mimeMessage);
            return true;
        } catch (Exception e) {
            System.out.println("Operation failed!");
        }
        return false;
    }

}
