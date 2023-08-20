package com.thejobslk.service;


import com.thejobslk.entity.Appointment;
import com.thejobslk.entity.Consultant;
import com.thejobslk.exception.ConsultantException;
import com.thejobslk.exception.TimeDateException;
import com.thejobslk.repository.ConsultantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ConsultantServiceImpl implements ConsultantService {

    @Autowired
    ConsultantDao consultantDao;


    @Override
    public List<Consultant> getAllConsultantsRegisteredFromDatabase() throws ConsultantException {

        List<Consultant> listOfConsultant = consultantDao.findAll();

        if(!listOfConsultant.isEmpty()) {

            return listOfConsultant;

        }else {

            throw new ConsultantException("No any consultants registered till" +
                    " " +
                    "now.");
        }
    }




    @Override
    public List<LocalDateTime> getConsultantAvailableTimingForBooking(String key,
                                                                  Consultant consultant) throws IOException, TimeDateException, ConsultantException {

        Optional<Consultant> registerconsultant =
                consultantDao.findById(consultant.getConsultantId());

        List<LocalDateTime> consultantAvailableTiming = new ArrayList<>();

        if(registerconsultant.isPresent()) {

            UserServiceImpl.loadAppointmentDates(registerconsultant.get().getAppointmentFromTime(), registerconsultant.get().getAppointmentToTime());

            Map<String, LocalDateTime> myTimeDate = UserServiceImpl.myTimeDate;

            List<Appointment> listOfConsultantsAppointment = registerconsultant.get().getListOfAppointments();





            for(String str: myTimeDate.keySet()) {

                Boolean flag = false;

                for(Appointment eachAppointment: listOfConsultantsAppointment) {

                    LocalDateTime localDateTime = myTimeDate.get(str);

                    if(localDateTime.isEqual(eachAppointment.getAppointmentDateAndTime())) {

                        flag = true;
                        break;

                    }
                }

                if(flag == false) {

                    consultantAvailableTiming.add(myTimeDate.get(str));

                }
            }

            if(!consultantAvailableTiming.isEmpty()) {

                return consultantAvailableTiming;

            }else {

                throw new ConsultantException("No time and date available to book appointment. " +
                        "Please try again");
            }


        }else {

            throw new ConsultantException("No Consultant found with this id " + consultant.getConsultantId());
        }



    }




}



