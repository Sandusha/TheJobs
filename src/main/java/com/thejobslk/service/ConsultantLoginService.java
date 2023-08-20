package com.thejobslk.service;


import com.thejobslk.entity.LoginDTO;
import com.thejobslk.entity.LoginUUIDKey;
import com.thejobslk.exception.LoginException;

public interface ConsultantLoginService {
	
	LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException;
	
	String logoutFromAccount(String key) throws LoginException;
	
	Boolean checkUserLoginOrNot(String key) throws LoginException;

}
