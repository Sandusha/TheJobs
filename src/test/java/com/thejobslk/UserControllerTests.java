package com.thejobslk;

import com.thejobslk.controller.UserController;
import com.thejobslk.entity.*;
import com.thejobslk.exception.*;
import com.thejobslk.service.ConsultantService;
import com.thejobslk.service.UserAndAdminLoginService;
import com.thejobslk.service.UserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserAndAdminLoginService loginService;

    @Mock
    private ConsultantService consultantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCustomer_Success() throws UserException {

        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.saveCustomer(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(user, response.getBody());

        // Verify that the required method was called
        verify(userService).createUser(user);
    }

    @Test
    public void testUpdateCustomer_Success() throws UserException {

        User user = new User();
        when(userService.updateUser(user, "valid_key")).thenReturn(user);

        ResponseEntity<User> response = userController.updateCustomer(user, "valid_key");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(user, response.getBody());

        // Verify that the required method was called
        verify(userService).updateUser(user, "valid_key");
    }

    @Test
    public void testGetUserDetails_Success() throws LoginException, UserException {

        String key = "valid_key";
        User user = new User();
        when(loginService.checkUserLoginOrNot(key)).thenReturn(true);
        when(userService.getUserDetails(key)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserDetails(key);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(user, response.getBody());

        // Verify that the required methods were called
        verify(loginService).checkUserLoginOrNot(key);
        verify(userService).getUserDetails(key);
    }

    @Test
    public void testBookAppointment_Success() throws LoginException, AppointmentException,
            ConsultantException, IOException, TimeDateException, MessagingException {

        String key = "valid_key";
        Appointment appointment = new Appointment();
        when(loginService.checkUserLoginOrNot(key)).thenReturn(true);
        when(userService.bookAppointment(key, appointment)).thenReturn(appointment);
        ResponseEntity<Appointment> response = userController.bookAppointment(key, appointment);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(appointment, response.getBody());
        // Verify that the required methods were called
        verify(loginService).checkUserLoginOrNot(key);
        verify(userService).bookAppointment(key, appointment);
    }

    @Test
    public void testGetAvailableTimingOfConsultant_Success() throws IOException, TimeDateException, LoginException, ConsultantException {

        String key = "valid_key";
        Consultant consultant = new Consultant();
        List<LocalDateTime> availableTiming = new ArrayList<>();
        when(loginService.checkUserLoginOrNot(key)).thenReturn(true);
        when(consultantService.getConsultantAvailableTimingForBooking(key, consultant)).thenReturn(availableTiming);
        ResponseEntity<List<LocalDateTime>> response = userController.getAvailableTimingOfConsultant(key, consultant);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(availableTiming, response.getBody());
        // Verify that the required methods were called
        verify(loginService).checkUserLoginOrNot(key);
        verify(consultantService).getConsultantAvailableTimingForBooking(key, consultant);
    }

    @Test
    public void testGetUserAllAppointment_Success() throws AppointmentException, UserException, LoginException {

        String key = "valid_key";
        List<Appointment> appointments = new ArrayList<>();
        when(loginService.checkUserLoginOrNot(key)).thenReturn(true);
        when(userService.getAppointmentsOfUser(key)).thenReturn(appointments);

        ResponseEntity<List<Appointment>> response = userController.GetUserAllAppointment(key);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(appointments, response.getBody());
        // Verify that the required methods were called
        verify(loginService).checkUserLoginOrNot(key);
        verify(userService).getAppointmentsOfUser(key);
    }

    @Test
    public void testGetAllConsultantsFromDataBase_Success() throws LoginException, ConsultantException {

        String key = "valid_key";
        List<Consultant> consultants = new ArrayList<>();
        when(loginService.checkUserLoginOrNot(key)).thenReturn(true);
        when(consultantService.getAllConsultantsInDatabase()).thenReturn(consultants);


        ResponseEntity<List<Consultant>> response = userController.getAllConsultantsFromDataBase(key);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(consultants, response.getBody());

        // Verify that the required methods were called
        verify(loginService).checkUserLoginOrNot(key);
        verify(consultantService).getAllConsultantsInDatabase();
    }

    @Test
    public void testGetAllConsultants_Success() throws LoginException, ConsultantException {

        String key = "valid_key";
        List<Consultant> consultants = new ArrayList<>();
        when(loginService.checkUserLoginOrNot(key)).thenReturn(true);
        when(userService.getAllConsultants()).thenReturn(consultants);


        ResponseEntity<List<Consultant>> response = userController.getAllConsultants(key);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(consultants, response.getBody());

        // Verify that the required methods were called
        verify(loginService).checkUserLoginOrNot(key);
        verify(userService).getAllConsultants();
    }

    @Test
    public void testDeleteAppointment_Success() throws AppointmentException, ConsultantException, Exception {

        String key = "valid_key";
        Appointment appointment = new Appointment();
        when(loginService.checkUserLoginOrNot(key)).thenReturn(true);
        when(userService.deleteAppointment(appointment)).thenReturn(appointment);


        ResponseEntity<Appointment> response = userController.deleteAppointment(key, appointment);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(appointment, response.getBody());

        // Verify that the required methods were called
        verify(loginService).checkUserLoginOrNot(key);
        verify(userService).deleteAppointment(appointment);
    }

    @Test
    public void testForgetPassword_Success() throws LoginException, PasswordException {

        String key = "valid_key";
        ForgetPassword forgetPassword = new ForgetPassword();
        forgetPassword.setOldPassword("old_password"); // Set old password
        forgetPassword.setNewPassword("new_password"); // Set new password
        forgetPassword.setConfirmNewPassword("new_password"); // Set confirm new password
        User user = new User();
        when(loginService.checkUserLoginOrNot(key)).thenReturn(true);
        when(userService.forgetPassword(key, forgetPassword)).thenReturn(user);


        ResponseEntity<User> response = userController.forgetPassword(key, forgetPassword);


        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertSame(user, response.getBody());

        // Verify that the required methods were called
        verify(loginService).checkUserLoginOrNot(key);
        verify(userService).forgetPassword(key, forgetPassword);
    }

}
