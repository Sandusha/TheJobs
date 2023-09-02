package com.thejobslk;

import com.thejobslk.controller.LoginController;
import com.thejobslk.entity.LoginDTO;
import com.thejobslk.entity.LoginResponse;
import com.thejobslk.entity.LoginUUIDKey;
import com.thejobslk.exception.LoginException;
import com.thejobslk.service.ConsultantLoginService;
import com.thejobslk.service.UserAndAdminLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTests {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserAndAdminLoginService userAndAdminLoginService;

    @Mock
    private ConsultantLoginService consultantLoginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginUser_Success() throws LoginException {

        LoginDTO loginDTO = new LoginDTO();
        LoginUUIDKey loginUUIDKey = new LoginUUIDKey();
        when(userAndAdminLoginService.logIntoAccount(loginDTO)).thenReturn(loginUUIDKey);


        ResponseEntity<LoginUUIDKey> response = loginController.loginUser(loginDTO);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(loginUUIDKey, response.getBody());

        // Verify that the required method was called
        verify(userAndAdminLoginService).logIntoAccount(loginDTO);
    }

    @Test
    public void testLoginConsultant_Success() throws LoginException {

        LoginDTO loginDTO = new LoginDTO();
        LoginUUIDKey loginUUIDKey = new LoginUUIDKey();
        when(consultantLoginService.logIntoAccount(loginDTO)).thenReturn(loginUUIDKey);


        ResponseEntity<LoginUUIDKey> response = loginController.loginConsultant(loginDTO);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(loginUUIDKey, response.getBody());

        // Verify that the required method was called
        verify(consultantLoginService).logIntoAccount(loginDTO);
    }

    @Test
    public void testCheckUserLoginORNot_Success() throws LoginException {

        String uuid = "valid_uuid";
        when(userAndAdminLoginService.checkUserLoginOrNot(uuid)).thenReturn(true);


        ResponseEntity<LoginResponse> response = loginController.checkUserLoginORNot(uuid);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getLoginOrNot());

        // Verify that the required method was called
        verify(userAndAdminLoginService).checkUserLoginOrNot(uuid);
    }

    @Test
    public void testLogoutUser_Success() throws LoginException {

        String key = "valid_key";
        when(userAndAdminLoginService.logoutFromAccount(key)).thenReturn("Logged out successfully.");


        String result = loginController.logoutUser(key);


        assertEquals("Logged out successfully.", result);

        // Verify that the required method was called
        verify(userAndAdminLoginService).logoutFromAccount(key);
    }

    @Test
    public void testLogoutConsultant_Success() throws LoginException {

        String key = "valid_key";
        when(consultantLoginService.logoutFromAccount(key)).thenReturn("Logged out successfully.");


        String result = loginController.logoutConsultant(key);


        assertEquals("Logged out successfully.", result);

        // Verify that the required method was called
        verify(consultantLoginService).logoutFromAccount(key);
    }
}
