package com.thejobslk.controller;

import com.thejobslk.entity.Appointment;
import com.thejobslk.entity.Consultant;
import com.thejobslk.entity.User;
import com.thejobslk.exception.*;
import com.thejobslk.service.ConsultantService;
import com.thejobslk.service.UserAndAdminLoginService;
import com.thejobslk.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("api/v4")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserAndAdminLoginService loginService;
	
	@Autowired
	ConsultantService consultantService;
	
	
	@CrossOrigin
	@PostMapping("/registerUser")
	public ResponseEntity<User> saveCustomer(@Valid @RequestBody User user) throws UserException {

		User savedUser= userService.createUser(user);
		
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/updateUser")
	public ResponseEntity<User> updateCustomer(@RequestBody User user, @RequestParam(required = false) String key) throws UserException{

		User updateduser = userService.updateUser(user, key);
		
		return new ResponseEntity<User>(updateduser,HttpStatus.OK);
		
	}

	/*
	@GetMapping("/UserDetails")
	@CrossOrigin
	public ResponseEntity<User> getUserDetails(@RequestParam String key) throws LoginException, UserException{
		
		if(loginService.checkUserLoginOrNot(key)) {

			User returnedUser = userService.getUserDetails(key);
			
			return new ResponseEntity<User>(returnedUser, HttpStatus
			.ACCEPTED);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
		
	}*/

	@PostMapping("/bookAppointment")
	@CrossOrigin
	public ResponseEntity<Appointment> bookAppointment(@RequestParam String key, @RequestBody Appointment appointment) throws LoginException, AppointmentException,
			ConsultantException, IOException, TimeDateException, MessagingException{

		if(appointment == null) {
			throw new AppointmentException("Please enter valid appointment " +
					"details");
		}

		if(loginService.checkUserLoginOrNot(key)) {

			Appointment registerAppointment = userService.bookAppointment(key,
					appointment);

			return new ResponseEntity<Appointment>(registerAppointment, HttpStatus.CREATED);

		}else {

			throw new LoginException("Invalid key or please login first");

		}

	}

	@PostMapping ("/availableTiming")
	@CrossOrigin
	public ResponseEntity<List<LocalDateTime>> getAvailableTimingOfConsultant(@RequestParam String key, @RequestBody Consultant consultant) throws IOException, TimeDateException, LoginException, ConsultantException{

		if(loginService.checkUserLoginOrNot(key)) {

			List<LocalDateTime> listOfAvailable =
					consultantService.getConsultantAvailableTimingForBooking(key,
							consultant);

			return new ResponseEntity<List<LocalDateTime>>(listOfAvailable, HttpStatus.ACCEPTED);


		}else {

			throw new LoginException("Invalid key or please login first");

		}
	}


}


