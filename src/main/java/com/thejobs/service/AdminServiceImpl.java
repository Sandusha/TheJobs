package com.thejobs.service;

import com.thejobs.config.SpringdocConfig;
import com.thejobs.entity.Consultant;
import com.thejobs.exception.ConsultantException;
import com.thejobs.repository.AppointmentDao;
import com.thejobs.repository.ConsultantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
  @Autowired
    ConsultantDao consultantDao;

  @Autowired
    AppointmentDao appointmentDao;

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











}
