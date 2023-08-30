package com.thejobslk.service;


import com.thejobslk.entity.*;
import com.thejobslk.exception.*;
import com.thejobslk.repository.ConsultantDao;
import com.thejobslk.repository.SessionDao;
import com.thejobslk.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.thejobslk.config.SpringdocConfig.bCryptPasswordEncoder;

@Service
public class ConsultantServiceImpl implements ConsultantService {

    @Autowired
    ConsultantDao consultantDao;
    @Autowired
    SessionDao sessionDao;
    @Autowired
    UserDao userDao;

    @Override
    public Consultant getConsultantDetails(String key) throws UserException {

        CurrentSession currentConsultant = sessionDao.findByUuid(key);

        Optional<Consultant> consultant = consultantDao.findById(currentConsultant.getUserId());

        if(consultant.isPresent()) {

            return consultant.get();

        }else {

            throw new UserException("Consultant not present by this uuid " + key);
        }


    }


    @Override
    public CurrentSession getCurrentUserByUuid(String uuid) throws LoginException {

        CurrentSession currentUserSession = sessionDao.findByUuid(uuid);

        if(currentUserSession != null) {

            return currentUserSession;

        }else {

            throw new LoginException("Please enter valid key");
        }
    }


    @Override
    public Consultant getConsultantByUuid(String uuid) throws UserException {

        CurrentSession currentConsultant = sessionDao.findByUuid(uuid);

        Optional<Consultant> consultant =
                consultantDao.findById(currentConsultant.getUserId());

        if(consultant.isPresent()) {

            return consultant.get();

        }else {

            throw new UserException("Consultant not present by this uuid " + uuid);
        }
    }



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
    public List<Appointment> getUpcommingAppointment(Consultant consultant) throws AppointmentException {

        List<Appointment> listOfAppointments = consultant.getListOfAppointments();

        List<Appointment> listOfCommingAppointmnet = new ArrayList<>();

        LocalDateTime currentTimeAndDate = LocalDateTime.now();


        try {

            for(Appointment eachAppointment: listOfAppointments) {

                if(eachAppointment.getAppointmentDateAndTime().isAfter(currentTimeAndDate)) {

                    listOfCommingAppointmnet.add(eachAppointment);
                }
            }
        }catch(Exception exception) {

            System.out.println(exception.fillInStackTrace());

        }

        if(!listOfCommingAppointmnet.isEmpty()) {

            return listOfCommingAppointmnet;

        }else {

            throw new AppointmentException("No upcoming appointments. Sorry!");

        }
    }


    @Override
    public List<Appointment> getPastAppointment(Consultant consultant) throws AppointmentException {

        List<Appointment> listOfAppointments = consultant.getListOfAppointments();

        List<Appointment> listOfPastAppointments = new ArrayList<>();

        LocalDateTime currentTimeAndDate = LocalDateTime.now();

        testing();


        try {

            for(Appointment eachAppointment: listOfAppointments) {

                if(eachAppointment.getAppointmentDateAndTime().isBefore(currentTimeAndDate)) {

                    listOfPastAppointments.add(eachAppointment);

                }

            }

        }catch(Exception exception) {


            System.out.println(exception.fillInStackTrace());

        }

        if(!listOfPastAppointments.isEmpty()) {

            return listOfPastAppointments;

        }else {

            throw new AppointmentException("No past appointments. Sorry!");

        }
    }


    public static void testing() {

        int strength = 10; // work factor of bcrypt

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

        String encodedPassword = bCryptPasswordEncoder.encode("1234");

    }






    @Override
    public List<Appointment> getAllAppointments(Consultant registerConsultant) throws ConsultantException {

        List<Appointment> listOfAppointments = registerConsultant.getListOfAppointments();

        if(!listOfAppointments.isEmpty()) {

            return listOfAppointments;

        }else {

            throw new ConsultantException("No appointments found.");
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


    @Override
    public List<Consultant> getAllConsultantsInDatabase() throws ConsultantException {

        List<Consultant> listOfConsultant = consultantDao.findAll();

        if(!listOfConsultant.isEmpty()) {

            return listOfConsultant;

        }else {

            throw new ConsultantException("Consultants have not registered " +
                    "yet.");
        }


    }

    @Override
    public List<User> getListOfUser() {

        List<User> listOfUser = userDao.findAll();

        return listOfUser;

    }

    @Override
    public Consultant forgetPassword(String key, ForgetPassword forgetPassword) throws PasswordException {

        CurrentSession currentUserSession = sessionDao.findByUuid(key);

        Optional<Consultant> registeredConsultant =
                consultantDao.findById(currentUserSession.getUserId());

        Boolean PasswordIsMatchingOrNot =
                bCryptPasswordEncoder.matches(forgetPassword.getOldPassword(), registeredConsultant.get().getPassword());

        if (PasswordIsMatchingOrNot) {

            registeredConsultant.get().setPassword(bCryptPasswordEncoder.encode(forgetPassword.getNewPassword()));

            return consultantDao.save(registeredConsultant.get());


        } else {

            throw new PasswordException("Old password does not match!.");

        }
    }


    @Override
    public Consultant updateTime(String key, UpdateTime updateTime) throws ConsultantException {

        CurrentSession currentUserSession = sessionDao.findByUuid(key);
        Optional<Consultant> registeredConsultant =
                consultantDao.findById(currentUserSession.getUserId());

        if (currentUserSession == null) {
            throw new ConsultantException("Please provide the valid key to " +
                    "update the times");
        } else {
            registeredConsultant.get().setAppointmentToTime(updateTime.getAppointmentToTime());
            registeredConsultant.get().setAppointmentFromTime(updateTime.getAppointmentFromTime());

            return consultantDao.save(registeredConsultant.get());
        }


    }
}



