package com.thejobslk.service;


import com.thejobslk.entity.Consultant;
import com.thejobslk.exception.ConsultantException;
import com.thejobslk.exception.TimeDateException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


public interface ConsultantService {

    List<Consultant> getAllConsultantsRegisteredFromDatabase() throws ConsultantException;


    List<LocalDateTime> getConsultantAvailableTimingForBooking(String key,
                                                           Consultant consultant) throws IOException, TimeDateException, ConsultantException;
}
