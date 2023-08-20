package com.thejobslk.service;



import com.thejobslk.entity.Consultant;
import com.thejobslk.entity.User;
import com.thejobslk.exception.ConsultantException;
import com.thejobslk.exception.UserException;

import java.util.List;

public interface AdminService {


	Consultant registerConsultant(Consultant consultant) throws ConsultantException;

	List<Consultant> getAllConsultants() throws ConsultantException;

	List<User> getAllUsers() throws UserException;
}
