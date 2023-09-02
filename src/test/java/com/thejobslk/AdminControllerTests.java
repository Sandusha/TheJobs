package com.thejobslk;

import com.thejobslk.controller.AdminController;
import com.thejobslk.entity.Appointment;
import com.thejobslk.entity.Consultant;
import com.thejobslk.entity.CurrentSession;
import com.thejobslk.entity.User;
import com.thejobslk.exception.*;
import com.thejobslk.service.AdminService;
import com.thejobslk.service.UserAndAdminLoginService;
import com.thejobslk.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerTests {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Mock
    private UserAndAdminLoginService userAndAdminLoginService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterConsultant_Success() throws ConsultantException, LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("admin");
        when(userService.getCurrentUserByUuid(anyString())).thenReturn(currentUserSession);


        Consultant consultant = new Consultant();
        when(adminService.registerConsultant(any(Consultant.class))).thenReturn(consultant);

        // Test registerConsultant endpoint
        ResponseEntity<Consultant> response = adminController.registerConsultant("valid_key", consultant);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(consultant, response.getBody());

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(userService).getCurrentUserByUuid("valid_key");
        verify(adminService).registerConsultant(consultant);
    }

    @Test
    public void testRegisterConsultant_InvalidKey() throws LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(false);


        try {
            adminController.registerConsultant("invalid_key", new Consultant());
        } catch (LoginException | ConsultantException e) {
            assertEquals("Invalid key or please login first.", e.getMessage());
        }

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("invalid_key");
        verifyNoInteractions(userService);
        verifyNoInteractions(adminService);
    }

    @Test
    public void testGetAllValidInValidConsultants_Success() throws LoginException, ConsultantException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("admin");
        when(userService.getCurrentUserByUuid(anyString())).thenReturn(currentUserSession);


        List<Consultant> consultantList = new ArrayList<>();
        when(adminService.getAllValidInValidConsultants(anyString())).thenReturn(consultantList);

        // Test getAllValidInValidConsultants endpoint
        ResponseEntity<List<Consultant>> response = adminController.getAllValidInValidConsultants("valid_key");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(consultantList, response.getBody());

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(userService).getCurrentUserByUuid("valid_key");
        verify(adminService).getAllValidInValidConsultants("valid_key");
    }

    @Test
    public void testGetAllValidInValidConsultants_InvalidUserType() throws LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("user");
        when(userService.getCurrentUserByUuid(anyString())).thenReturn(currentUserSession);

        // Test getAllValidInValidConsultants endpoint with an invalid user type
        try {
            adminController.getAllValidInValidConsultants("valid_key");
        } catch (LoginException | ConsultantException e) {
            assertEquals("Please login as admin", e.getMessage());
        }

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(userService).getCurrentUserByUuid("valid_key");
        verifyNoInteractions(adminService);
    }

    @Test
    public void testGetAllUsers_Success() throws LoginException, UserException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        List<User> userList = new ArrayList<>();
        when(adminService.getAllUsers()).thenReturn(userList);

        // Test getAllUsers endpoint
        ResponseEntity<List<User>> response = adminController.getAllUsers("valid_key");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(userList, response.getBody());

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(adminService).getAllUsers();
    }

    @Test
    public void testGetAllUsers_InvalidKey() throws LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(false);

        // Test getAllUsers endpoint with an invalid key
        try {
            adminController.getAllUsers("invalid_key");
        } catch (LoginException | UserException e) {
            assertEquals("Invalid key or please login first.", e.getMessage());
        }

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("invalid_key");
        verifyNoInteractions(adminService);
    }


    @Test
    public void testGetAllAppointments_Success() throws LoginException, AppointmentException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        List<Appointment> appointmentList = new ArrayList<>();
        when(adminService.getAllAppointments()).thenReturn(appointmentList);

        // Test getAllAppointments endpoint
        ResponseEntity<List<Appointment>> response = adminController.getAllAppointments("valid_key");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(appointmentList, response.getBody());

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(adminService).getAllAppointments();
    }

    @Test
    public void testGetAllAppointments_InvalidKey() throws LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(false);

        // Test getAllAppointments endpoint with an invalid key
        try {
            adminController.getAllAppointments("invalid_key");
        } catch (LoginException | AppointmentException e) {
            assertEquals("Invalid key or please login first", e.getMessage());
        }

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("invalid_key");
        verifyNoInteractions(adminService);
    }


    @Test
    public void testRevokePermissionOfConsultant_Success() throws LoginException, ConsultantException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("admin");
        when(userService.getCurrentUserByUuid(anyString())).thenReturn(currentUserSession);


        Consultant consultant = new Consultant();
        when(adminService.revokePermissionOfConsultant(any(Consultant.class))).thenReturn(consultant);

        // Test revokePermissionOfConsultant endpoint
        ResponseEntity<Consultant> response = adminController.revokePermissionOfConsultant("valid_key", consultant);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(consultant, response.getBody());

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(userService).getCurrentUserByUuid("valid_key");
        verify(adminService).revokePermissionOfConsultant(consultant);
    }

    @Test
    public void testRevokePermissionOfConsultant_InvalidKey() throws LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(false);


        try {
            adminController.revokePermissionOfConsultant("invalid_key", new Consultant());
        } catch (LoginException | ConsultantException e) {
            assertEquals("Please enter valid key.", e.getMessage());
        }

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("invalid_key");
        verifyNoInteractions(userService);
        verifyNoInteractions(adminService);
    }

    @Test
    public void testRevokePermissionOfConsultant_InvalidUserType() throws LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("user");
        when(userService.getCurrentUserByUuid(anyString())).thenReturn(currentUserSession);

        // Test revokePermissionOfConsultant endpoint with an invalid user type
        try {
            adminController.revokePermissionOfConsultant("valid_key", new Consultant());
        } catch (LoginException | ConsultantException e) {
            assertEquals("Please login as admin", e.getMessage());
        }

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(userService).getCurrentUserByUuid("valid_key");
        verifyNoInteractions(adminService);
    }

    @Test
    public void testGrantPermissionOfConsultant_Success() throws LoginException, ConsultantException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("admin");
        when(userService.getCurrentUserByUuid(anyString())).thenReturn(currentUserSession);


        Consultant consultant = new Consultant();
        when(adminService.grantPermissionOfConsultant(any(Consultant.class))).thenReturn(consultant);

        // Test grantPermissionOfConsultant endpoint
        ResponseEntity<Consultant> response = adminController.grantPermissionOfConsultant("valid_key", consultant);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(consultant, response.getBody());

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(userService).getCurrentUserByUuid("valid_key");
        verify(adminService).grantPermissionOfConsultant(consultant);
    }

    @Test
    public void testGrantPermissionOfConsultant_InvalidKey() throws LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(false);

        // Test grantPermissionOfConsultant endpoint with an invalid key
        try {
            adminController.grantPermissionOfConsultant("invalid_key", new Consultant());
        } catch (LoginException | ConsultantException e) {
            assertEquals("Please enter valid key.", e.getMessage());
        }

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("invalid_key");
        verifyNoInteractions(userService);
        verifyNoInteractions(adminService);
    }

    @Test
    public void testGrantPermissionOfConsultant_InvalidUserType() throws LoginException {

        when(userAndAdminLoginService.checkUserLoginOrNot(anyString())).thenReturn(true);


        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("user");
        when(userService.getCurrentUserByUuid(anyString())).thenReturn(currentUserSession);

        // Test grantPermissionOfConsultant endpoint with an invalid user type
        try {
            adminController.grantPermissionOfConsultant("valid_key", new Consultant());
        } catch (LoginException | ConsultantException e) {
            assertEquals("Please login as admin", e.getMessage());
        }

        // Verify that the required methods were called
        verify(userAndAdminLoginService).checkUserLoginOrNot("valid_key");
        verify(userService).getCurrentUserByUuid("valid_key");
        verifyNoInteractions(adminService);
    }
}
