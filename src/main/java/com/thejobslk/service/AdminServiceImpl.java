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

			consultant.setType("consultant");

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

	@Override
	public List<Consultant> getAllValidInValidConsultants(String key) throws ConsultantException {

		List<Consultant> listOfConsultants = consultantDao.findAll();

		if(!listOfConsultants.isEmpty()) {

			return listOfConsultants;

		}else {

			throw new ConsultantException("No Consultants register. Please " +
					"contact admin.");

		}
	}

	@Override
	public Consultant revokePermissionOfConsultant(Consultant consultant) throws ConsultantException {

		Optional<Consultant> registerConsultant =
				consultantDao.findById(consultant.getConsultantId());

		if(registerConsultant.isPresent()) {

			registerConsultant.get().setValidConsultant(false);

			return consultantDao.save(registerConsultant.get());

		}else {

			throw new ConsultantException("valid Consultant not present with " +
					"this id " + consultant.getConsultantId());

		}
	}

	@Override
	public Consultant grantPermissionOfConsultant(Consultant consultant) throws ConsultantException {

		Optional<Consultant> registerConsultant =
				consultantDao.findById(consultant.getConsultantId());

		if(registerConsultant.isPresent()) {

			registerConsultant.get().setValidConsultant(true);

			return consultantDao.save(registerConsultant.get());

		}else {

			throw new ConsultantException("Doctor not present with this id " + consultant.getConsultantId());

		}
	}








}
