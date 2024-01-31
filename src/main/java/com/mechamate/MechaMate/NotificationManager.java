package com.mechamate.MechaMate;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class NotificationManager {
    private final String username;
    private final String password;
    private final String smtpHost;
    private final int smtpPort;

    public NotificationManager(String username, String password, String smtpHost, int smtpPort) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public void sendEmailVerification(String recipientEmail, String verificationLink) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try  {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Verification Link");

            String htmlContent = "<html>\n" +
                    "  <head></head>\n" +
                    "  <body bgcolor=\"#ff0000\">\n" +
                    "    <h1>Hi!<br>\n" +
                    "       How are you?<br>\n" +
                    "       Here is the <a href=\"" + verificationLink + "\">link</a> you wanted.\n" +
                    "    </h1>\n" +
                    "  </body>\n" +
                    "</html>";

            message.setContent(htmlContent, "text/html");

            Transport.send(message);

            System.out.println("Email verification sent to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email verification.", e);
        }
    }

    public static void main(String[] args) {
        // Example usage
        String username = "mechamate@outlook.com";
        String password = ""; // Add your password here
        String smtpHost = "smtp.office365.com";
        int smtpPort = 587;

        NotificationManager notificationManager = new NotificationManager(username, password, smtpHost, smtpPort);
        notificationManager.sendEmailVerification("mechamateproject@gmail.com", "https://www.google.com");
    }
}
