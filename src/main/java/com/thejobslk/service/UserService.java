package com.thejobslk.service;


import com.thejobslk.entity.*;
import com.thejobslk.exception.*;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;


public interface UserService {
	
	User createUser(User customer) throws UserException;

	User updateUser(User user, String key) throws UserException;



	List<Appointment> getAppointmentsOfUser(String key)throws AppointmentException, UserException;

	List<Consultant> getAllConsultants() throws ConsultantException;

	User getUserByUuid(String uuid) throws UserException;
	
	CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
	User forgetPassword(String key, ForgetPassword forgetPassword) throws PasswordException;
	Appointment deleteAppointment(Appointment appointment) throws AppointmentException, ConsultantException, Exception;

	User getUserDetails(String key) throws UserException;
	
	Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, ConsultantException, IOException, TimeDateException, MessagingException;






	
}
