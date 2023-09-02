package com.thejobslk;

import com.thejobslk.controller.ConsultantController;
import com.thejobslk.entity.*;
import com.thejobslk.exception.*;
import com.thejobslk.service.ConsultantLoginService;
import com.thejobslk.service.ConsultantService;
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

public class ConsultantControllerTests {

    @InjectMocks
    private ConsultantController consultantController;

    @Mock
    private ConsultantLoginService consultantLoginService;

    @Mock
    private ConsultantService consultantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetConsultantDetails_ValidKey_ReturnsConsultant() throws LoginException, UserException {

        String validKey = "validKey";
        Consultant consultant = new Consultant();
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);
        when(consultantService.getConsultantDetails(validKey)).thenReturn(consultant);


        ResponseEntity<Consultant> response = consultantController.getConsultantDetails(validKey);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(consultant, response.getBody());
    }

    @Test
    public void testGetConsultantDetails_InvalidKey_ThrowsLoginException() throws LoginException, UserException {

        String invalidKey = "invalidKey";
        when(consultantLoginService.checkUserLoginOrNot(invalidKey)).thenReturn(false);


        assertThrows(LoginException.class, () -> consultantController.getConsultantDetails(invalidKey));
    }

    @Test
    public void testGetUpcomingAppointments_ValidKey_ReturnsAppointments() throws LoginException, UserException, ConsultantException, AppointmentException {

        String validKey = "validKey";
        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("consultant");
        Consultant registerConsultant = new Consultant();
        List<Appointment> appointments = new ArrayList<>();
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);
        when(consultantService.getCurrentUserByUuid(validKey)).thenReturn(currentUserSession);
        when(consultantService.getConsultantByUuid(validKey)).thenReturn(registerConsultant);
        when(consultantService.getUpcommingAppointment(registerConsultant)).thenReturn(appointments);


        ResponseEntity<List<Appointment>> response = consultantController.getUpcomingAppointments(validKey);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(appointments, response.getBody());
    }

    @Test
    public void testGetUpcomingAppointments_InvalidKey_ThrowsLoginException() throws LoginException, UserException, ConsultantException, AppointmentException {

        String invalidKey = "invalidKey";
        when(consultantLoginService.checkUserLoginOrNot(invalidKey)).thenReturn(false);


        assertThrows(LoginException.class, () -> consultantController.getUpcomingAppointments(invalidKey));
    }

    @Test
    public void testGetPastAppointments_ValidKey_ReturnsAppointments() throws LoginException, UserException, AppointmentException, ConsultantException {

        String validKey = "validKey";
        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("consultant");
        Consultant registerConsultant = new Consultant();
        List<Appointment> appointments = new ArrayList<>();
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);
        when(consultantService.getCurrentUserByUuid(validKey)).thenReturn(currentUserSession);
        when(consultantService.getConsultantByUuid(validKey)).thenReturn(registerConsultant);
        when(consultantService.getPastAppointment(registerConsultant)).thenReturn(appointments);


        ResponseEntity<List<Appointment>> response = consultantController.getPastAppointments(validKey);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(appointments, response.getBody());
    }

    @Test
    public void testGetPastAppointments_InvalidKey_ThrowsLoginException() throws LoginException, UserException, AppointmentException, ConsultantException {

        String invalidKey = "invalidKey";
        when(consultantLoginService.checkUserLoginOrNot(invalidKey)).thenReturn(false);


        assertThrows(LoginException.class, () -> consultantController.getPastAppointments(invalidKey));
    }

    @Test
    public void testGetAllAppointments_ValidKey_ReturnsAppointments() throws LoginException, UserException, AppointmentException, ConsultantException {

        String validKey = "validKey";
        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("consultant");
        Consultant registerConsultant = new Consultant();
        List<Appointment> appointments = new ArrayList<>();
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);
        when(consultantService.getCurrentUserByUuid(validKey)).thenReturn(currentUserSession);
        when(consultantService.getConsultantByUuid(validKey)).thenReturn(registerConsultant);
        when(consultantService.getAllAppointments(registerConsultant)).thenReturn(appointments);


        ResponseEntity<List<Appointment>> response = consultantController.getAllAppointments(validKey);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(appointments, response.getBody());
    }

    @Test
    public void testGetAllAppointments_InvalidKey_ThrowsLoginException() throws LoginException, UserException, AppointmentException, ConsultantException {

        String invalidKey = "invalidKey";
        when(consultantLoginService.checkUserLoginOrNot(invalidKey)).thenReturn(false);


        assertThrows(LoginException.class, () -> consultantController.getAllAppointments(invalidKey));
    }

    @Test
    public void testGetAllListOfUsers_ValidKey_ReturnsListOfUsers() throws ConsultantException, LoginException, UserException {

        String validKey = "validKey";
        CurrentSession currentUserSession = new CurrentSession();
        currentUserSession.setUserType("consultant");
        Consultant registerConsultant = new Consultant();
        List<User> users = new ArrayList<>();
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);
        when(consultantService.getCurrentUserByUuid(validKey)).thenReturn(currentUserSession);
        when(consultantService.getConsultantByUuid(validKey)).thenReturn(registerConsultant);
        when(consultantService.getListOfUser()).thenReturn(users);


        ResponseEntity<List<User>> response = consultantController.getAllListOfUsers(validKey);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(users, response.getBody());
    }

    @Test
    public void testGetAllListOfUsers_InvalidKey_ThrowsLoginException() throws ConsultantException, LoginException, UserException {

        String invalidKey = "invalidKey";
        when(consultantLoginService.checkUserLoginOrNot(invalidKey)).thenReturn(false);


        assertThrows(LoginException.class, () -> consultantController.getAllListOfUsers(invalidKey));
    }

    @Test
    public void testForgetPassword_ValidKeyAndMatchingPasswords_ReturnsConsultant() throws LoginException, PasswordException {

        String validKey = "validKey";
        ForgetPassword forgetPassword = new ForgetPassword();
        forgetPassword.setOldPassword("oldPassword");
        forgetPassword.setNewPassword("newPassword");
        forgetPassword.setConfirmNewPassword("newPassword");
        Consultant consultant = new Consultant();
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);
        when(consultantService.forgetPassword(validKey, forgetPassword)).thenReturn(consultant);


        ResponseEntity<Consultant> response = consultantController.forgetPassword(validKey, forgetPassword);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(consultant, response.getBody());
    }

    @Test
    public void testForgetPassword_ValidKeyAndNonMatchingPasswords_ThrowsPasswordException() throws LoginException, PasswordException {

        String validKey = "validKey";
        ForgetPassword forgetPassword = new ForgetPassword();
        forgetPassword.setOldPassword("oldPassword");
        forgetPassword.setNewPassword("newPassword");
        forgetPassword.setConfirmNewPassword("differentPassword");
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);


        assertThrows(PasswordException.class, () -> consultantController.forgetPassword(validKey, forgetPassword));
    }

    @Test
    public void testForgetPassword_ValidKeyAndSameOldNewPasswords_ThrowsPasswordException() throws LoginException, PasswordException {

        String validKey = "validKey";
        ForgetPassword forgetPassword = new ForgetPassword();
        forgetPassword.setOldPassword("oldPassword");
        forgetPassword.setNewPassword("oldPassword");
        forgetPassword.setConfirmNewPassword("oldPassword");
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);


        assertThrows(PasswordException.class, () -> consultantController.forgetPassword(validKey, forgetPassword));
    }

    @Test
    public void testForgetPassword_InvalidKey_ThrowsLoginException() throws LoginException, PasswordException {

        String invalidKey = "invalidKey";
        ForgetPassword forgetPassword = new ForgetPassword();
        when(consultantLoginService.checkUserLoginOrNot(invalidKey)).thenReturn(false);


        assertThrows(LoginException.class, () -> consultantController.forgetPassword(invalidKey, forgetPassword));
    }

    @Test
    public void testTimeUpdate_ValidKeyAndValidTime_ReturnsConsultant() throws LoginException, ConsultantException {

        String validKey = "validKey";
        UpdateTime updateTime = new UpdateTime();
        updateTime.setAppointmentFromTime(9);
        updateTime.setAppointmentToTime(10);
        Consultant consultant = new Consultant();
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);
        when(consultantService.updateTime(validKey, updateTime)).thenReturn(consultant);


        ResponseEntity<Consultant> response = consultantController.timeUpdate(validKey, updateTime);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(consultant, response.getBody());
    }

    @Test
    public void testTimeUpdate_ValidKeyAndInvalidTime_ThrowsConsultantException() throws LoginException, ConsultantException {

        String validKey = "validKey";
        UpdateTime updateTime = new UpdateTime();
        updateTime.setAppointmentFromTime(10);
        updateTime.setAppointmentToTime(9);
        when(consultantLoginService.checkUserLoginOrNot(validKey)).thenReturn(true);


        assertThrows(ConsultantException.class, () -> consultantController.timeUpdate(validKey, updateTime));
    }

    @Test
    public void testTimeUpdate_InvalidKey_ThrowsLoginException() throws LoginException, ConsultantException {

        String invalidKey = "invalidKey";
        UpdateTime updateTime = new UpdateTime();
        when(consultantLoginService.checkUserLoginOrNot(invalidKey)).thenReturn(false);


        assertThrows(LoginException.class, () -> consultantController.timeUpdate(invalidKey, updateTime));
    }
}
