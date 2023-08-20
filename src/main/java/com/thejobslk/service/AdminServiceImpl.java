package com.thejobslk.service;

import com.thejobslk.config.SpringdocConfig;
import com.thejobslk.entity.Consultant;
import com.thejobslk.entity.User;
import com.thejobslk.exception.ConsultantException;
import com.thejobslk.exception.UserException;
import com.thejobslk.repository.AppointmentDao;
import com.thejobslk.repository.ConsultantDao;
import com.thejobslk.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	ConsultantDao consultantDao;
	
	@Autowired
	AppointmentDao appointmentDao;
	@Autowired
	UserDao userDao;

	@Override
	public Consultant registerConsultant(Consultant consultant) throws ConsultantException
	{
		Consultant databaseConsultant =
				consultantDao.findByMobileNo(consultant.getMobileNo()) ;

		if(databaseConsultant == null) {

			consultant.setType("Consultant");

			consultant.setPassword(SpringdocConfig.bCryptPasswordEncoder.encode(consultant.getPassword()));

			return consultantDao.save(consultant);

		}else {

			throw new ConsultantException("Consultant already register with" +
					"This Number " + consultant.getMobileNo());
		}


	}

	@Override
	public List<Consultant> getAllConsultants() throws ConsultantException {

		List<Consultant> listOfConsultant = consultantDao.findAll();

		if(!listOfConsultant.isEmpty()) {

			return listOfConsultant;

		}else {

			throw new ConsultantException("No any consultants registered till" +
					" " +
					"now" +
					".");
		}
	}
	@Override
	public List<User> getAllUsers() throws UserException {

		List<User> listOfUser = userDao.findAll();

		if(!listOfUser.isEmpty()) {

			return listOfUser;

		}else {

			throw new UserException("No any users registered till" +
					" " +
					"now" +
					".");
		}
	}











}
