package com.mechamate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mechamate.common.Common;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.UserProfile;
import com.mechamate.service.DatabaseAbstractLayer;
import com.mechamate.service.LanguageManager;
import com.mechamate.service.NotificationManager;
import com.mechamate.service.ProfileManager;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProfileManagerTest {

    @Mock
    private DatabaseAbstractLayer databaseAbstractLayer;

    @Mock
    private NotificationManager notificationManager;

    @Mock
    private LanguageManager lang;

    @InjectMocks
    private ProfileManager profileManager;

    @Mock
    private HttpServletRequest request;
    private UserProfile userProfile;
    private String correctPassword = "correctPassword";
    private String incorrectPassword = "incorrectPassword";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userProfile = new UserProfile();
        userProfile.setUsername("ado");
        userProfile.setEmail("adooo@xml.com");
        userProfile.setStatus(UserProfile.Status.StatusActive);
        userProfile.setLanguage("en");
        userProfile.setPassword(Common.getSha256("AUTH#>>(" + correctPassword + ")<<#"));

        when(lang.get(anyString(), anyString())).thenReturn("error message");
    }



    @Test
    public void testCreateUserProfile_UserExists() {
        when(databaseAbstractLayer.isUserExists(userProfile.getUsername())).thenReturn(true);

        ResponseEntity<ErrorDTO> response = profileManager.createUserProfile(request, userProfile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ErrorDTO.ErrorStatus.ErrorUserExists, response.getBody().getError());
    }

    @Test
    public void testCreateUserProfile_EmailExists() {
        // mock the database responses for user and email existence checks.
        when(databaseAbstractLayer.isUserExists(userProfile.getUsername())).thenReturn(false);
        when(databaseAbstractLayer.isEmailExists(userProfile.getEmail())).thenReturn(true);

        ResponseEntity<ErrorDTO> response = profileManager.createUserProfile(request, userProfile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ErrorDTO.ErrorStatus.ErrorEmailExists, response.getBody().getError());
    }



    @Test
    public void testCreateUserProfile_FailureToAddUserProfile() {
        //mocking part
        when(databaseAbstractLayer.isUserExists(userProfile.getUsername())).thenReturn(false);
        when(databaseAbstractLayer.isEmailExists(userProfile.getEmail())).thenReturn(false);
        when(databaseAbstractLayer.addUserProfile(userProfile)).thenReturn(false);

        ResponseEntity<ErrorDTO> response = profileManager.createUserProfile(request, userProfile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ErrorDTO.ErrorStatus.InternalError, response.getBody().getError());
    }



    @Test
    public void testSignin_MaxLoginAttemptsExceeded() {
        // simulating more than 5 unsuccessful login attempts
        userProfile.setUnsuccessfulLoginAttempts(6);
        userProfile.setLastLoginAttemptTime(System.currentTimeMillis() - 1000);

        ResponseEntity<ErrorDTO> response = profileManager.signin(request, userProfile.getUsername(), incorrectPassword, userProfile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ErrorDTO.ErrorStatus.ErrorMaxAttemptCountExceeded, response.getBody().getError());
    }

    @Test
    public void testSignin_ProfileInactive() {
        userProfile.setStatus(UserProfile.Status.StatusInactive);

        ResponseEntity<ErrorDTO> response = profileManager.signin(request, userProfile.getUsername(), correctPassword, userProfile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(null, response.getBody());
    }
}
