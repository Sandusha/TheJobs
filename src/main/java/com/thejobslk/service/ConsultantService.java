package com.thejobslk.service;


import com.thejobslk.entity.Appointment;
import com.thejobslk.entity.Consultant;
import com.thejobslk.entity.CurrentSession;
import com.thejobslk.entity.User;
import com.thejobslk.exception.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


public interface ConsultantService {
    Consultant getConsultantDetails(String key) throws UserException;

    Consultant getConsultantByUuid(String uuid) throws UserException;
    CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
    List<Appointment> getUpcommingAppointment(Consultant consultant) throws AppointmentException;
    List<Appointment> getPastAppointment(Consultant consultant) throws AppointmentException;
    List<Appointment> getAllAppointments(Consultant registerConsultant) throws ConsultantException;

    List<Consultant> getAllConsultantsRegisteredFromDatabase() throws ConsultantException;


    List<Consultant> getAllConsultantsInDatabase() throws ConsultantException;

    List<User> getListOfUser();

    List<LocalDateTime> getConsultantAvailableTimingForBooking(String key,
                                                           Consultant consultant) throws IOException, TimeDateException, ConsultantException;
}
