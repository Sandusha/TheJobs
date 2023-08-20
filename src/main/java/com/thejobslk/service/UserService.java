package com.thejobslk.service;


import com.thejobslk.entity.Appointment;
import com.thejobslk.entity.Consultant;
import com.thejobslk.entity.CurrentSession;
import com.thejobslk.entity.User;
import com.thejobslk.exception.*;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;


public interface UserService {
	
	User createUser(User customer) throws UserException;

	User updateUser(User user, String key) throws UserException;

	User getUserByUuid(String uuid) throws UserException;
	
	CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
	
	Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, ConsultantException, IOException, TimeDateException, MessagingException;

	List<Consultant> getAllConsultants() throws ConsultantException;




	
}
