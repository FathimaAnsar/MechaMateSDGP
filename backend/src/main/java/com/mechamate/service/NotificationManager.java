
/*
    Author:     B.D.C JAYASANKA
    Copyright:  © 2023, All rights reserved
*/


package com.mechamate.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.mechamate.entity.UserProfile;

@Service
public class NotificationManager {

    @Autowired
    private JavaMailSender mailClient;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.config.server.address}")
    private String hostname;

    public boolean sendActivationEmail(UserProfile userProfile) {

        String emailTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>MechaMate Account Activation</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 20px auto;
                            padding: 20px;
                            background-color: #fff;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                        h1 {
                            color: navy;
                        }
                        p {
                            color: #333;
                            line-height: 1.6;
                        }
                        .button {
                            display: inline-block;
                            background-color: navy;
                            color: #fff;
                            padding: 10px 20px;
                            text-decoration: none;
                            border-radius: 5px;
                        }
                        .button:hover {
                            background-color: #001f3f;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Welcome to MechaMate!</h1><br>
                        <h3>Hello [REPLACE1]!</h3>
                        <p>Thank you for joining MechaMate, your trusted vehicle maintenance prediction application. We're excited to have you onboard!</p>
                        <p>To activate your account and start predicting your vehicle's maintenance needs, please click the button below:</p>
                        <a class="button" href="[REPLACE2]" target="_blank">Activate Account</a>
                        <p>If you have any questions or need assistance, feel free to contact our support team.</p>
                        <p>Best regards,<br>The MechaMate Team</p>
                    </div>
                </body>
                </html>
                """;
        emailTemplate = emailTemplate.replace("[REPLACE1]", userProfile.getFirstname());
        emailTemplate = emailTemplate.replace("[REPLACE2]",
                hostname + "/activate.html?key=" + userProfile.getActivationKey());

        return sendEmail(emailTemplate, userProfile, "MechaMate Account Activation");
    }


    public boolean sendWelcomeEmail(UserProfile userProfile) {

        String emailTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Welcome to MechaMate!</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 20px auto;
                            padding: 20px;
                            background-color: #fff;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                        h1 {
                            color: navy;
                        }
                        p {
                            color: #333;
                            line-height: 1.6;
                        }
                        .button {
                            display: inline-block;
                            background-color: navy;
                            color: #fff;
                            padding: 10px 20px;
                            text-decoration: none;
                            border-radius: 5px;
                        }
                        .button:hover {
                            background-color: #001f3f;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Welcome to MechaMate!</h1>
                        <p>Congratulations on successfully activating your MechaMate account. You're now part of a community that helps you predict your vehicle's maintenance needs efficiently.</p>
                        <p>With MechaMate, you can:</p>
                        <ul>
                            <li>Receive timely alerts for upcoming maintenance tasks</li>
                            <li>Monitor your vehicle's health and performance</li>
                            <li>Access valuable insights to optimize maintenance schedules</li>
                        </ul>
                        <p>Start exploring our features now by logging into your account.</p>
                        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>
                        <p>Thank you for choosing MechaMate!</p>
                        <p>Best regards,<br>The MechaMate Team</p>
                    </div>
                </body>
                </html>
                """;
        return sendEmail(emailTemplate, userProfile, "Welcome to MechaMate!");
    }



    public boolean sendLoginAlert(UserProfile userProfile) {
        String emailTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Login Alert: Your MechaMate Account</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 20px auto;
                            padding: 20px;
                            background-color: #fff;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                        h1 {
                            color: navy;
                        }
                        p {
                            color: #333;
                            line-height: 1.6;
                        }
                        .login-details {
                            margin-bottom: 20px;
                        }
                        .button {
                            display: inline-block;
                            background-color: navy;
                            color: #fff;
                            padding: 10px 20px;
                            text-decoration: none;
                            border-radius: 5px;
                        }
                        .button:hover {
                            background-color: #001f3f;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Login Alert: Your MechaMate Account</h1>
                        <p>Hello [REPLACE1],</p>
                        <p>We noticed that your MechaMate account was recently accessed. Here are the details:</p>
                        <div class="login-details">
                            <p><strong>Date & Time:</strong> [Login Date & Time]</p>
                            <p><strong>IP Address:</strong> [User's IP Address]</p>
                            <p><strong>Location:</strong> [User's Location]</p>
                        </div>
                        <p>If this login was not authorized by you, please secure your account immediately by changing your password and contacting our support team.</p>
                        <p>If you did log in, no further action is required. You can continue to enjoy using MechaMate for all your vehicle maintenance needs.</p>
                        <p>If you have any questions or concerns, feel free to reach out to us.</p>
                        <p>Best regards,<br>The MechaMate Team</p>
                    </div>
                </body>
                </html>
                """;
        emailTemplate = emailTemplate.replace("[REPLACE1]", userProfile.getFirstname());
        return sendEmail(emailTemplate, userProfile, "Login Alert: Your MechaMate Account");
    }



    public boolean sendRecoveryEmail(UserProfile userProfile) {
        String emailTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>MechaMate Password Reset</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 20px auto;
                            padding: 20px;
                            background-color: #fff;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                        h1 {
                            color: navy;
                        }
                        p {
                            color: #333;
                            line-height: 1.6;
                        }
                        .button {
                            display: inline-block;
                            background-color: navy;
                            color: #fff;
                            padding: 10px 20px;
                            text-decoration: none;
                            border-radius: 5px;
                        }
                        .button:hover {
                            background-color: #001f3f;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>MechaMate Password Reset</h1>
                        <p>Hello [REPLACE1],</p>
                        <p>We received a request to reset the password for your MechaMate account. If you did not make this request, you can safely ignore this email.</p>
                        <p>To reset your password, please click the button below:</p>
                        <a class="button" href="[REPLACE2]" target="_blank">Reset Password</a>
                        <p>If the button above doesn't work, you can copy and paste the following link into your browser's address bar:</p>
                        <p>[PASSWORD_RESET_URL]</p>
                        <p>This link will expire in 30 minutes for security reasons. If you need further assistance, please contact our support team.</p>
                        <p>Best regards,<br>The MechaMate Team</p>
                    </div>
                </body>
                </html>                
                """;
        emailTemplate = emailTemplate.replace("[REPLACE1]", userProfile.getFirstname());
        emailTemplate = emailTemplate.replace("[REPLACE2]",
                hostname + "/reset.html?token=" + userProfile.getRecoveryKey());

        return sendEmail(emailTemplate, userProfile, "MechaMate Password Reset");
    }


    public boolean sendResetConfirmEmail(UserProfile userProfile) {
        String emailTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Password Reset Confirmation - MechaMate</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 20px auto;
                            padding: 20px;
                            background-color: #fff;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                        h1 {
                            color: navy;
                        }
                        p {
                            color: #333;
                            line-height: 1.6;
                        }
                        .button {
                            display: inline-block;
                            background-color: navy;
                            color: #fff;
                            padding: 10px 20px;
                            text-decoration: none;
                            border-radius: 5px;
                        }
                        .button:hover {
                            background-color: #001f3f;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Password Reset Successful</h1>
                        <p>Hello [REPLACE1],</p>
                        <p>Your password for MechaMate has been successfully reset. You can now log in using your new password.</p>
                        <p>If you did not request this password reset or believe your account has been compromised, please contact our support team immediately.</p>
                        <p>Thank you for using MechaMate!</p>
                        <p>Best regards,<br>The MechaMate Team</p>
                    </div>
                </body>
                </html>                
                """;
        emailTemplate = emailTemplate.replace("[REPLACE1]", userProfile.getFirstname());
        return sendEmail(emailTemplate, userProfile, "Password Reset Confirmation");
    }


    private boolean sendEmail(String emailTemplate, UserProfile userProfile, String subject) {
        try {
            MimeMessage mimeMessage = mailClient.createMimeMessage();
            MimeMessageHelper email = new MimeMessageHelper(mimeMessage, "UTF-8");
            email.setSubject(subject);
            email.setFrom(sender);
            email.setTo(userProfile.getEmail());
            email.setText(emailTemplate, true);
            mailClient.send(mimeMessage);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
