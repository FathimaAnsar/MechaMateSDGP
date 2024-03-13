package com.mechamate;

import com.mechamate.common.Validation;
import com.mechamate.controller.AuthController;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.dto.SuccessDTO;
import com.mechamate.entity.Session;
import com.mechamate.entity.UserProfile;
import com.mechamate.service.LanguageManager;
import com.mechamate.service.ProfileManager;
import com.mechamate.service.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private SessionManager sessionManager;

    @Mock
    private ProfileManager profileManager;

    @Mock
    private LanguageManager lang;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignin_userAlreadySignedIn() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
        Session session = new Session();
        when(sessionManager.getSession(request, response)).thenReturn(session);

        when(Validation.alreadySignedIn(any(), any(), any())).thenReturn(new ResponseEntity<>(
                new ErrorDTO(ErrorDTO.ErrorStatus.InternalError, "already signed in", "you are already signed in"),
                HttpStatus.BAD_REQUEST));

        ResponseEntity<?> result = authController.signin(request, response, "username", "password", false);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.getBody() instanceof ErrorDTO);
        ErrorDTO errorDTO = (ErrorDTO) result.getBody();
        assertEquals(ErrorDTO.ErrorStatus.InternalError, errorDTO.getError());
    }

    @Test
    public void testSignout_userSignedOutSuccessfully() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);

        Session session = mock(Session.class);
        UserProfile userProfile = mock(UserProfile.class);
        when(session.getUserProfile()).thenReturn(userProfile);
        when(sessionManager.getSession(request, response)).thenReturn(session);
        when(profileManager.signout(any(HttpServletRequest.class), any(UserProfile.class))).thenReturn(null);
        when(sessionManager.deleteSession(any(HttpServletRequest.class), any(HttpServletResponse.class), any(UserProfile.class))).thenReturn(null);

        String language = "en";
        when(userProfile.getLanguage()).thenReturn(language);
        when(lang.get("success.signout.succeeded", language)).thenReturn("Sign out succeeded.");
        when(lang.get("success.signout.succeeded.info", language)).thenReturn("You have been signed out.");

        ResponseEntity<?> result = authController.singout(request,response);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof SuccessDTO);

        SuccessDTO successDTO = (SuccessDTO) result.getBody();
        assertEquals(SuccessDTO.SuccessStatus.SignoutSucceeded, successDTO.getStatus());
        assertEquals("Sign out succeeded.", successDTO.getMessage());
    }

}
