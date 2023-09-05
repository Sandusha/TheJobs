package com.thejobslk.controller;

import com.thejobslk.entity.*;
import com.thejobslk.exception.*;
import com.thejobslk.repository.SessionDao;
import com.thejobslk.repository.UserDao;
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
import java.util.Optional;


@RestController
@RequestMapping("api/v4")
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	UserDao userDao;
	
	@Autowired
	UserAndAdminLoginService loginService;
	
	@Autowired
	ConsultantService consultantService;

	@Autowired
	SessionDao sessionDao;
	
	
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


	@GetMapping("/UserDetails")
	@CrossOrigin
	public ResponseEntity<User> getUserDetails(@RequestParam String key) throws LoginException, UserException {

		if (loginService.checkUserLoginOrNot(key)) {

			User returnedUser = userService.getUserDetails(key);

			return new ResponseEntity<User>(returnedUser, HttpStatus
					.ACCEPTED);

		} else {

			throw new LoginException("Invalid key or please login first");

		}
	}


	@PostMapping("/bookAppointment")
	@CrossOrigin
	public ResponseEntity<Appointment> bookAppointment(@RequestParam String key,
	 @RequestBody Appointment appointment) throws LoginException, AppointmentException,
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
	public ResponseEntity<List<LocalDateTime>> getAvailableTimingOfConsultant(@RequestParam String key,
		 @RequestBody Consultant consultant) throws IOException, TimeDateException, LoginException, ConsultantException{

		if(loginService.checkUserLoginOrNot(key)) {

			List<LocalDateTime> listOfAvailable =
					consultantService.getConsultantAvailableTimingForBooking(key,
							consultant);

			return new ResponseEntity<List<LocalDateTime>>(listOfAvailable, HttpStatus.ACCEPTED);


		}else {

			throw new LoginException("Invalid key or please login first");

		}
	}


	@GetMapping("/allAppointment")
	@CrossOrigin
	public ResponseEntity<List<Appointment>> GetUserAllAppointment(@RequestParam String key) throws
			AppointmentException, UserException, LoginException{

		if(loginService.checkUserLoginOrNot(key)) {

			List<Appointment> listOfAppointments =
					userService.getAppointmentsOfUser(key);

			return new ResponseEntity<List<Appointment>>(listOfAppointments, HttpStatus.ACCEPTED);


		}else {

			throw new LoginException("Invalid key or please login first");

		}

	}





	@GetMapping("/allConsultants")
	@CrossOrigin
	public ResponseEntity<List<Consultant>> getAllConsultantsFromDataBase(@RequestParam String key) throws LoginException, ConsultantException{

		if(loginService.checkUserLoginOrNot(key)) {

			List<Consultant> ListOfConsultants =
					consultantService.getAllConsultantsInDatabase();

			return new ResponseEntity<List<Consultant>>(ListOfConsultants, HttpStatus.ACCEPTED);


		}else {

			throw new LoginException("Invalid key or please login first");

		}
	}

	@GetMapping("/getAllConsultants")
	@CrossOrigin
	public ResponseEntity<List<Consultant>> getAllConsultants(@RequestParam String key) throws LoginException, ConsultantException{
		if(loginService.checkUserLoginOrNot(key)) {

			List<Consultant> listOfConsultants = userService.getAllConsultants();

			return new ResponseEntity<List<Consultant>>(listOfConsultants,
					HttpStatus.ACCEPTED);


		}else {

			throw new LoginException("Invalid key or please login first");

		}
	}


	@DeleteMapping("/cancelappointment")
	@CrossOrigin
	public ResponseEntity<Appointment> deleteAppointment(@RequestParam String key, @RequestBody Appointment appointment) throws AppointmentException, ConsultantException, Exception{

		if(loginService.checkUserLoginOrNot(key)) {

			Appointment ConsultantList =
					userService.deleteAppointment(appointment);

			return new ResponseEntity<Appointment>(ConsultantList, HttpStatus.ACCEPTED);


		}else {

			throw new LoginException("Invalid key or please login first");

		}
	}

	@PutMapping("/forgetPassword")
	@CrossOrigin
	public ResponseEntity<User> forgetPassword(@RequestParam String key,
											@RequestBody ForgetPassword forgetPassword) throws LoginException, PasswordException {

		if(loginService.checkUserLoginOrNot(key)) {

			if(forgetPassword.getNewPassword().equals(forgetPassword.getConfirmNewPassword())) {

				if(forgetPassword.getOldPassword().equals(forgetPassword.getNewPassword())) {

					throw new PasswordException("Please enter new password.");

				}

				User finalResult = userService.forgetPassword(key,
						forgetPassword);

				return new ResponseEntity<User>(finalResult,
						HttpStatus.ACCEPTED);

			}else {

				throw new PasswordException("Confirm password and new " +
						"password do not match!");

			}

		}else {

			throw new LoginException("Invalid key or please login first");

		}
	}





}








