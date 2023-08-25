package com.thejobslk.controller;
import com.thejobslk.entity.Appointment;
import com.thejobslk.entity.Consultant;
import com.thejobslk.entity.CurrentSession;
import com.thejobslk.entity.User;
import com.thejobslk.exception.AppointmentException;
import com.thejobslk.exception.ConsultantException;
import com.thejobslk.exception.LoginException;
import com.thejobslk.exception.UserException;
import com.thejobslk.service.ConsultantLoginService;
import com.thejobslk.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v3")
public class ConsultantController {
	
	@Autowired
	ConsultantLoginService consultantLoginService;
	
	@Autowired
	ConsultantService consultantService;

	@GetMapping("/getConsultantDetails")
	@CrossOrigin
	public ResponseEntity<Consultant> getConsultantDetails(@RequestParam String key) throws LoginException, UserException {

		if(consultantLoginService.checkUserLoginOrNot(key)) {

			Consultant returnConsultant = consultantService.getConsultantDetails(key);

			return new ResponseEntity<Consultant>(returnConsultant, HttpStatus.ACCEPTED);

		}else {

			throw new LoginException("Please enter valid key");

		}
	}



	@GetMapping("/upcomingAppointments")
	@CrossOrigin
	public ResponseEntity<List<Appointment>> getUpcomingAppointments(@RequestParam String key) throws LoginException, UserException, ConsultantException, AppointmentException {

		if(consultantLoginService.checkUserLoginOrNot(key)) {

			CurrentSession currentUserSession =
					consultantService.getCurrentUserByUuid(key);

			Consultant registerConsultant = consultantService.getConsultantByUuid(key);

			if(!currentUserSession.getUserType().equals("consultant")) {

				throw new LoginException("Please login as consultant");

			}

			if(registerConsultant != null) {

				List<Appointment> listOfUpCommingAppointment =
						consultantService.getUpcommingAppointment(registerConsultant);

				return new ResponseEntity<List<Appointment>>(listOfUpCommingAppointment, HttpStatus.ACCEPTED);


			}else {

				throw new ConsultantException("Please enter valid consultant " +
						"details");

			}

		}else {

			throw new LoginException("Please enter valid key");

		}

	}


	@GetMapping("/pastAppointments")
	@CrossOrigin
	public ResponseEntity<List<Appointment>> getPastAppointments(@RequestParam String key) throws LoginException, UserException, AppointmentException, ConsultantException{

		if(consultantLoginService.checkUserLoginOrNot(key)) {

			CurrentSession currentUserSession = consultantService.getCurrentUserByUuid(key);

			Consultant registerConsultant = consultantService.getConsultantByUuid(key);

			if(!currentUserSession.getUserType().equals("consultant")) {

				throw new LoginException("Please login as consultant");

			}

			if(registerConsultant != null) {

				List<Appointment> listOfUpPastAppointment =
						consultantService.getPastAppointment(registerConsultant);

				return new ResponseEntity<List<Appointment>>(listOfUpPastAppointment, HttpStatus.ACCEPTED);


			}else {

				throw new ConsultantException("Please enter valid consultant " +
						"details");

			}

		}else {

			throw new LoginException("Please enter valid key");

		}

	}



	@GetMapping("/getAllAppointments")
	@CrossOrigin
	public ResponseEntity<List<Appointment>> getAllAppointments(@RequestParam String key) throws LoginException, UserException, AppointmentException, ConsultantException{

		if(consultantLoginService.checkUserLoginOrNot(key)) {

			CurrentSession currentUserSession = consultantService.getCurrentUserByUuid(key);

			Consultant registerConsultant = consultantService.getConsultantByUuid(key);

			if(!currentUserSession.getUserType().equals("consultant")) {

				throw new LoginException("Please login as consultant");

			}

			if(registerConsultant != null) {

				List<Appointment> listOfUpPastAppointment =
						consultantService.getAllAppointments(registerConsultant);

				return new ResponseEntity<List<Appointment>>(listOfUpPastAppointment, HttpStatus.ACCEPTED);


			}else {

				throw new ConsultantException("Please enter valid consultant details");

			}

		}else {

			throw new LoginException("Please enter valid key");

		}

	}

	@GetMapping("/listOfUsers")
	@CrossOrigin
	public ResponseEntity<List<User>> getAllListOfUsers(@RequestParam String key) throws ConsultantException, LoginException, UserException{

		if(consultantLoginService.checkUserLoginOrNot(key)) {

			CurrentSession currentUserSession = consultantService.getCurrentUserByUuid(key);

			Consultant registerConsultant = consultantService.getConsultantByUuid(key);

			if(!currentUserSession.getUserType().equals("consultant")) {

				throw new LoginException("Please login as consultant");

			}

			if(registerConsultant != null) {

				List<User> listOfUser = consultantService.getListOfUser();

				return new ResponseEntity<List<User>>(listOfUser, HttpStatus.OK);

			}else {

				throw new ConsultantException("Please enter valid consultant details");

			}

		}else {

			throw new LoginException("Please enter valid key");

		}
	}




}


























