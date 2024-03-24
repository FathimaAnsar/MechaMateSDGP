package com.mechamate;

import com.mechamate.entity.UserProfile;
import com.mechamate.service.NotificationManager;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificationManagerTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private NotificationManager notificationManager;

    @Captor
    private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }


    @Test
    public void sendEmail_Failure() throws Exception {
        UserProfile userProfile = createUserProfile();
        doThrow(new RuntimeException("Mail server not available")).when(javaMailSender).send(any(MimeMessage.class));

        assertFalse(notificationManager.sendActivationEmail(userProfile));

    }

    private UserProfile createUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername("Hansila");
        userProfile.setEmail("ado@why.com");
        userProfile.setFirstname("hansi");
        userProfile.setActivationKey("123456");
        return userProfile;
    }
}