package com.thejobs.service;

import com.thejobs.config.SpringdocConfig;
import com.thejobs.entity.Consultant;
import com.thejobs.entity.User;
import com.thejobs.exception.ConsultantException;
import com.thejobs.exception.UserException;
import com.thejobs.repository.AppointmentDao;
import com.thejobs.repository.ConsultantDao;
import com.thejobs.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
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
              consultantDao.findByEmail(consultant.getEmail()) ;

      if(databaseConsultant == null) {

          consultant.setType("Consultant");

          consultant.setPassword(SpringdocConfig.bCryptPasswordEncoder.encode(consultant.getPassword()));

          return consultantDao.save(consultant);

      }else {

          throw new ConsultantException("Consultant already register with" +
                  "Email " + consultant.getEmail());
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
