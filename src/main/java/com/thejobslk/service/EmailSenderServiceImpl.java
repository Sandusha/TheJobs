package com.thejobslk.service;


import com.thejobslk.entity.EmailBody;
import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService{
	

	private JavaMailSender javaMailSender;

	public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public Boolean sendAppointmentBookingMail(String toEmail, EmailBody emailBody) throws MessagingException {
		
		SimpleMailMessage Emessege = new SimpleMailMessage();
		
		Emessege.setFrom("Dushadeshan@yandex.com");
		
		Emessege.setTo(toEmail);
		Emessege.setText(emailBody.getEmailBody());
		Emessege.setSubject(emailBody.getEmailSubject());
		
		javaMailSender.send(Emessege);
		
		
		return true;
	
	}

}
