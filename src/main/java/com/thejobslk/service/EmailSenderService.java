package com.thejobslk.service;


import com.thejobslk.entity.EmailBody;
import jakarta.mail.MessagingException;

public interface EmailSenderService {
	
	Boolean sendAppointmentBookingMail(String toEmail, EmailBody emailBody) throws MessagingException;
	

}
