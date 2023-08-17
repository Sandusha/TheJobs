package com.thejobs.service;

import com.thejobs.config.SpringdocConfig;
import com.thejobs.entity.CurrentSession;
import com.thejobs.entity.LoginDTO;
import com.thejobs.entity.LoginUUIDKey;
import com.thejobs.entity.User;
import com.thejobs.exception.LoginException;
import com.thejobs.repository.SessionDao;
import com.thejobs.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserAndAdminLoginServiceImpl implements UserAndAdminLoginService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException {
		
		LoginUUIDKey loginUUIDKey = new LoginUUIDKey();
		
		User existingUser = userDao.findByEmail(loginDTO.getEmail());
		
		if(existingUser == null) {
			throw new LoginException("Please enter valid Email " + loginDTO.getEmail());
		}
		
		
		Optional<CurrentSession> validCustomerSessionOpt =
				sessionDao.findById(existingUser.getUserId());
		
		////////////////////////////////
		
		// this code is for only frontend application
		
		if(validCustomerSessionOpt.isPresent()) {
			
			if(SpringdocConfig.bCryptPasswordEncoder.matches(loginDTO.getPassword(),
					existingUser.getPassword())){
				
				loginUUIDKey.setUuid(validCustomerSessionOpt.get().getUuid());
				loginUUIDKey.setMsg("Login Successful");
				return loginUUIDKey;
				
			}
			
			throw new LoginException("Please enter valid details");
		
			
		}
		

		
		

		// please do uncomment this code while using this application in postman

	if(validCustomerSessionOpt.isPresent()) {
		throw new LoginException("User already logged in!");

		}
		
		
		if(SpringdocConfig.bCryptPasswordEncoder.matches(loginDTO.getPassword(),
				existingUser.getPassword())) {
			
			String key = generateRandomString();
			
			CurrentSession currentUserSession =
					new CurrentSession(existingUser.getUserId(), key,
							LocalDateTime.now());
			
			
			if(SpringdocConfig.bCryptPasswordEncoder.matches("admin",
					existingUser.getPassword()) && existingUser.getEmail().equals(
							"Admin@admin.com")) {
				
				existingUser.setType("admin");
				currentUserSession.setUserType("admin");
				currentUserSession.setUserId(existingUser.getUserId());
				
				sessionDao.save(currentUserSession);
				userDao.save(existingUser);
				
				loginUUIDKey.setMsg("Successfully logged in as Admin with key");
				
				loginUUIDKey.setUuid(key);
				
				return loginUUIDKey;
				
				
			}else {
				
				existingUser.setType("user");
				currentUserSession.setUserId(existingUser.getUserId());
				currentUserSession.setUserType("user");
				
			}
			
			userDao.save(existingUser);
			
			sessionDao.save(currentUserSession);
			
			loginUUIDKey.setMsg("Successfully logged in  as User with this" +
					" key");
			
			loginUUIDKey.setUuid(key);
			
			return loginUUIDKey;
		
		}else {
			
			throw new LoginException("Please enter valid password");
			
		}
	}

	@Override
	public String logoutFromAccount(String key) throws LoginException {
		
		CurrentSession currentUserOptional = sessionDao.findByUuid(key);
		
		if(currentUserOptional != null) {
			
			sessionDao.delete(currentUserOptional);
			
			return "Successfully logged out";
			
		}else {
			
			throw new LoginException("Please enter valid key");
			
		}
	}
	
	@Override
	public Boolean checkUserLoginOrNot(String key) throws LoginException { 
		
		CurrentSession currentUserSession = sessionDao.findByUuid(key);
		
		if(currentUserSession != null) {
			
			return true;
			
		}else {
			
			return false;
		}
		
	}
	
	public static String generateRandomString() {
		
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
	}

}
