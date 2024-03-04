package com.mechamate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mechamate.common.Common;
import com.mechamate.service.DatabaseAbstractLayer;
import com.mechamate.service.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;

import com.mechamate.entity.Session;
import com.mechamate.entity.UserProfile;

public class SessionManagerTest {

    @Mock
    private DatabaseAbstractLayer databaseAbstractLayer;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Logger logger;

    @InjectMocks
    private SessionManager sessionManager;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }
    @Captor
    private ArgumentCaptor<Cookie> cookieCaptor;


    @Test
    public void testGetSessionValid() {
        // Setup
        UserProfile mockUserProfile = mock(UserProfile.class);
        when(mockUserProfile.getUsername()).thenReturn("username");
        when(mockUserProfile.getPassword()).thenReturn("password");

        // Simulate the session key creation in the actual method's style
        String baseString = "SKEY#>" + mockUserProfile.getUsername() + System.currentTimeMillis() + mockUserProfile.getPassword() + "<#";
        String sessionKey = Common.getSha256(baseString);

        Cookie sessionCookie = new Cookie("sessionKey", sessionKey);
        Cookie[] cookies = new Cookie[] { sessionCookie };

        when(request.getCookies()).thenReturn(cookies);

        long validUntil = System.currentTimeMillis() + 3600000; // 1 hour ahead
        Session mockSession = new Session(validUntil, sessionKey, mockUserProfile);

        // Define behavior to return the mock session and to simulate successful update
        when(databaseAbstractLayer.getSession(sessionKey)).thenReturn(mockSession);
        when(databaseAbstractLayer.updateSession(any(Session.class))).thenReturn(true);

        // Execution
        Session resultSession = sessionManager.getSession(request, response);

        // Verification
        assertNotNull(resultSession, "Session should not be null for a valid request");
        verify(databaseAbstractLayer, times(1)).getSession(sessionKey);
        verify(databaseAbstractLayer, times(1)).updateSession(any(Session.class));
    }




    @Test
    public void testGetSessionNoCookies() {
        // Setup
        when(request.getCookies()).thenReturn(null);

        // Execution
        Session resultSession = sessionManager.getSession(request, response);

        // Verification
        assertNull(resultSession, "Session should be null when no cookies are present");
    }



}