//
//package com.mechamate.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import jakarta.mail.MessagingException; // Import from jakarta.mail
//import jakarta.mail.internet.MimeMessage; // Import from jakarta.mail
//
//@Service
//public class NotificationService {
//
//    public static void main(String[] args) {
//        SpringApplication.run(NotificationService.class, args);
//    }
//
//    @Autowired
//    private JavaMailSender emailSender;
//
//    @Value("${spring.mail.username}")
//    private String emailUsername;
//
//    @Value("${spring.mail.password}")
//    private String emailPassword;
//
//    @Value("${spring.mail.host}")
//    private String emailHost;
//
//    @Value("${spring.mail.port}")
//    private int emailPort;
//
//    public void sendEmailVerification(String recipientEmail, String verificationLink) {
//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
//
//        try {
//            String htmlMsg = "<html><body>" +
//                    "<h2>Verify Your Email Address</h2>" +
//                    "<p>Click the button below to verify your email address:</p>" +
//                    "<a href=\"" + verificationLink + "\" style=\"padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px;\">Verify Email Address</a>" +
//                    "</body></html>";
//
//            helper.setTo(recipientEmail);
//            helper.setSubject("Email Verification");
//            helper.setText(htmlMsg, true);
//
//            emailSender.send(message);
//
//            System.out.println("Email verification sent to " + recipientEmail);
//        } catch (MessagingException e) {
//            throw new RuntimeException("Failed to send email verification.", e);
//        }
//    }
//}
//
//
///*
//package com.mechamate.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import jakarta.mail.MessagingException; // Import from jakarta.mail
//import jakarta.mail.internet.MimeMessage; // Import from jakarta.mail
//
//
//@SpringBootApplication
//public class NotificationService {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Notification.class, args);
//    }
//
//    @Autowired
//    private Notification notificationService;
//
//    @Bean
//    public CommandLineRunner demo() {
//        return args -> {
//            String recipientEmail = "recipient@example.com";
//            String verificationLink = "https://example.com/verify"; // Your actual verification link here
//
//            // Trigger the email verification
//            notificationService.sendEmailVerification(recipientEmail, verificationLink);
//        };
//    }
//
//    @Service
//    public static class Notification {
//
//        @Autowired
//        private JavaMailSender emailSender;
//
//        @Value("${spring.mail.username}")
//        private String emailUsername;
//
//        @Value("${spring.mail.password}")
//        private String emailPassword;
//
//        @Value("${spring.mail.host}")
//        private String emailHost;
//
//        @Value("${spring.mail.port}")
//        private int emailPort;
//
//        public void sendEmailVerification(String recipientEmail, String verificationLink) {
//            MimeMessage message = emailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
//
//            try {
//                String htmlMsg = "<html><body>" +
//                        "<h2>Verify Your Email Address</h2>" +
//                        "<p>Click the button below to verify your email address:</p>" +
//                        "<a href=\"" + verificationLink + "\" style=\"padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px;\">Verify Email Address</a>" +
//                        "</body></html>";
//
//                helper.setTo(recipientEmail);
//                helper.setSubject("Email Verification");
//                helper.setText(htmlMsg, true);
//
//                emailSender.send(message);
//
//                System.out.println("Email verification sent to " + recipientEmail);
//            } catch (MessagingException e) {
//                throw new RuntimeException("Failed to send email verification.", e);
//            }
//        }
//    }
//}
//
//*/
//
//
//
//
//
///*
//import java.util.Properties;
//import javax.mail.*;
//import javax.mail.internet.*;
//
//public class NotificationManager {
//    private final String username;
//    private final String password;
//    private final String smtpHost;
//    private final int smtpPort;
//
//    public NotificationManager(String username, String password, String smtpHost, int smtpPort) {
//        this.username = username;
//        this.password = password;
//        this.smtpHost = smtpHost;
//        this.smtpPort = smtpPort;
//    }
//
//    public void sendEmailVerification(String recipientEmail, String verificationLink) {
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", smtpHost);
//        props.put("mail.smtp.port", smtpPort);
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try  {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//            message.setSubject("Verification Link");
//
//            String htmlContent = "<html>\n" +
//                    "  <head></head>\n" +
//                    "  <body bgcolor=\"#ff0000\">\n" +
//                    "    <h1>Hi!<br>\n" +
//                    "       How are you?<br>\n" +
//                    "       Here is the <a href=\"" + verificationLink + "\">link</a> you wanted.\n" +
//                    "    </h1>\n" +
//                    "  </body>\n" +
//                    "</html>";
//
//            message.setContent(htmlContent, "text/html");
//
//            Transport.send(message);
//
//            System.out.println("Email verification sent to " + recipientEmail);
//        } catch (MessagingException e) {
//            throw new RuntimeException("Failed to send email verification.", e);
//        }
//    }
//
//    public static void main(String[] args) {
//        // Example usage
//        String username = "mechamate@outlook.com";
//        String password = ""; // Add your password here
//        String smtpHost = "smtp.office365.com";
//        int smtpPort = 587;
//
//        NotificationManager notificationManager = new NotificationManager(username, password, smtpHost, smtpPort);
//        notificationManager.sendEmailVerification("mechamateproject@gmail.com", "https://www.google.com");
//    }
//}
//*/
