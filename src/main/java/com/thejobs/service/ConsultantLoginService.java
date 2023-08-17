package com.thejobs.service;


import com.thejobs.entity.LoginDTO;
import com.thejobs.entity.LoginUUIDKey;
import com.thejobs.exception.LoginException;

public interface ConsultantLoginService {
	
	LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException;
	
	String logoutFromAccount(String key) throws LoginException;
	
	Boolean checkUserLoginOrNot(String key) throws LoginException;

}
