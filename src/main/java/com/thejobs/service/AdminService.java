package com.thejobs.service;

import com.thejobs.entity.Consultant;
import com.thejobs.entity.User;
import com.thejobs.exception.ConsultantException;
import com.thejobs.exception.UserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    Consultant registerConsultant (Consultant consultant) throws ConsultantException;
    List<Consultant> getAllConsultants() throws ConsultantException;
    List<User> getAllUsers() throws UserException;




}
