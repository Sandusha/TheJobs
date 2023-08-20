package com.thejobslk.service;

import com.thejobslk.entity.*;
import com.thejobslk.exception.*;
import com.thejobslk.repository.AppointmentDao;
import com.thejobslk.repository.ConsultantDao;
import com.thejobslk.repository.SessionDao;
import com.thejobslk.repository.UserDao;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.thejobslk.config.SpringdocConfig.bCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService, Runnable {

    public static Map<String, LocalDateTime> myTimeDate = new LinkedHashMap<>();

    @Autowired
    UserDao userDao;

    @Autowired
    SessionDao sessionDao;

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    ConsultantDao consultantDao;

    @Autowired
    Appointment savedAppointment;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    EmailBody emailBody;


    public UserServiceImpl(Appointment appointment, EmailSenderService emailSenderService, EmailBody emailBody) {

        this.savedAppointment = appointment;
        this.emailSenderService = emailSenderService;
        this.emailBody = emailBody;

    }


    @Override
    public User createUser(User user) throws UserException {

        User databaseUser = userDao.findByMobileNo(user.getMobileNo());

        if (databaseUser == null) {

            // setting type user

            user.setType("User");
  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            userDao.save(user);

            return user;


        } else {

            throw new UserException("User already register with this mobile " +
                    "no " + user.getMobileNo());

        }
    }

    @Override
    public User updateUser(User user, String key) throws UserException {


        CurrentSession loggedInUser = sessionDao.findByUuid(key);

        if (loggedInUser == null) {

            throw new UserException("Please provide the valid key to update the user");
        }

        if (user.getUserId() == loggedInUser.getUserId()) {

            return userDao.save(user);

        } else {
            throw new UserException("Invalid user details. Login first");
        }
    }

    @Override
    public User getUserByUuid(String uuid) throws UserException {

        CurrentSession currentUser = sessionDao.findByUuid(uuid);

        Optional<User> user = userDao.findById(currentUser.getUserId());

        if (user.isPresent()) {

            return user.get();

        } else {

            throw new UserException("User or Admin not available by this uuid " + uuid);
        }
    }

    @Override
    public CurrentSession getCurrentUserByUuid(String uuid) throws LoginException {

        CurrentSession currentUserSession = sessionDao.findByUuid(uuid);

        if (currentUserSession != null) {

            return currentUserSession;

        } else {

            throw new LoginException("Please enter valid key");
        }
    }

    public static void loadAppointmentDates(Integer from, Integer to) throws IOException, TimeDateException {
  myTimeDate.clear();
 if (from == null || to == null) {

            throw new TimeDateException("Please enter valid Consultant appointment From to To time");
        }

       /* FileReader reader = new FileReader("config.properties");

        Properties p = new Properties();

        p.load(reader);
*/
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime tomorrowDateTime = currentDateTime.plusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (int i = from; i <= to; i++) {

            String TodaytimeString = null;

            if (!(i >= 10)) {

                TodaytimeString = currentDateTime.toLocalDate() + " 0" + i + ":00";


            } else {

                TodaytimeString = currentDateTime.toLocalDate() + " " + i + ":00";

            }

            LocalDateTime dateTime = LocalDateTime.parse(TodaytimeString, formatter);

            // we are checking if time is gone or not if time is gone then don't put in database

            // 2023-03-09 01:00

            if (currentDateTime.isBefore(dateTime)) {

                myTimeDate.put("today" + i, dateTime);

            }

        }

        // puting next day dates

        for (int i = from; i <= to; i++) {

            String tomorrowTimeString = null;

            if (!(i >= 10)) {

                tomorrowTimeString = tomorrowDateTime.toLocalDate() + " 0" + i + ":00";

            } else {

                tomorrowTimeString = tomorrowDateTime.toLocalDate() + " " + i + ":00";

            }

            LocalDateTime dateTime = LocalDateTime.parse(tomorrowTimeString, formatter);

            // checking if time is gone or not if time is gone ,it won't save in
            // database
            if (currentDateTime.isBefore(dateTime)) {

                myTimeDate.put("tomorrow" + i, dateTime);

            }

        }

    }

    @Override
    public Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, ConsultantException, IOException, TimeDateException, MessagingException {

        CurrentSession currentUserSession = sessionDao.findByUuid(key);

        Optional<User> user = userDao.findById(currentUserSession.getUserId());


        synchronized (this) {

            if (user.isPresent()) {

                // setting user in appointment
                appointment.setUser(user.get());

                Consultant consultant = appointment.getConsultant();

                Optional<Consultant> registerConsultant =
                        consultantDao.findById(consultant.getConsultantId());

                if (!registerConsultant.isEmpty()) {

                    // setting Consultant in appointment
                    appointment.setConsultant(registerConsultant.get());

                    // check if appointment date and time is available or not
                  loadAppointmentDates(registerConsultant.get().getAppointmentFromTime(), registerConsultant.get().getAppointmentToTime());
                 List<Appointment> listOfAppointment =
                 appointment.getConsultant().getListOfAppointments();

                    Boolean flag1 = false;

                    Boolean flag2 = false;

                    for (Appointment eachAppointment : listOfAppointment) {

                        if (eachAppointment.getAppointmentDateAndTime().isEqual(appointment.getAppointmentDateAndTime())) {

                            flag1 = true;

                        }
                    }

                    // check if given date and time if correct or not

                    for (String str : myTimeDate.keySet()) {

                        if (myTimeDate.get(str).isEqual(appointment.getAppointmentDateAndTime())) {

                            flag2 = true;

                        }
                    }


                    Appointment registerAppointment = null;


                    if (!flag1 && flag2) {


                        registerAppointment = appointmentDao.save(appointment);

                        // email to user

                        emailBody.setEmailBody("Dear Sir/Ma'am, \n You have " +
                                "booked an appointment with " + registerAppointment.getConsultant().getName() +
                                ". Please make sure to join on time. If you " +
                                "want to call a consultant please contact " + registerAppointment.getConsultant().getMobileNo() + "\n"

                                + "\n"
                                + "Appointment Id: " + registerAppointment.getAppointmentId() + "\n"
                                + "Consultant specialty: " + registerAppointment.getConsultant().getSpecialty() + "\n"
                                + "Consultant experience: " + registerAppointment.getConsultant().getExperience() + "\n"
                                + "\n"

                                + "Thanks and Regards \n"
                                + "TheJobs.LK");

                        emailBody.setEmailSubject("You have successfully book appointment at " + registerAppointment.getAppointmentDateAndTime());

                        UserServiceImpl userServiceImpl  =
                                new UserServiceImpl(appointment,
                                emailSenderService, emailBody);

                        Thread emailSentThread = new Thread(userServiceImpl);

                        // Multi-Threading

                        emailSentThread.start();

                    } else {

                        throw new AppointmentException("This time or date already booked or please enter valid appointment time and date " + appointment.getAppointmentDateAndTime());

                    }
                    // mapping appointment in Consultant and then saving
                    // Consultant


                    registerConsultant.get().getListOfAppointments().add(registerAppointment);

                    consultantDao.save(registerConsultant.get());

                    // mapping appointment in user then saving user

                    user.get().getListOfAppointments().add(registerAppointment);

                    userDao.save(user.get());


                    return registerAppointment;


                } else {

                    throw new ConsultantException("Please enter valid " +
                            "Consultant" +
                            " details or Consultant" +
                            " not present with this id " + consultant.getConsultantId());

                }


            } else {

                throw new LoginException("Please enter valid key");

            }

        }


    }

    @Override
    public List<Consultant> getAllConsultants() throws ConsultantException {

        List<Consultant> ConsultantList = consultantDao.findAll();

        if (!ConsultantList.isEmpty()) {

            ConsultantList = ConsultantList.stream().collect(Collectors.toList());

            return ConsultantList;

        } else {

            throw new ConsultantException("No Consultants registered. Please " +
                    "contact admin.");

        }

    }



    @Override
    public void run() {

        // sending mail to user for successfully booking of appointment


        try {

            // sending mail to user for successfully booking of appointment


            emailSenderService.sendAppointmentBookingMail(savedAppointment.getUser().getEmail(),emailBody);

        } catch (MessagingException e) {

            e.printStackTrace();
        }

    }


  /*  @Override
    public User getUserDetails(String key) throws UserException {

        CurrentSession currentUserSession = sessionDao.findByUuid(key);

        Optional<User> registerUser = userDao.findById(currentUserSession.getUserId());

        if (registerUser.isPresent()) {

            return registerUser.get();


        } else {

            throw new UserException("User not found.");
        }
    }
    */
}






