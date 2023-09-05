package com.thejobslk.service;

import com.thejobslk.config.SpringdocConfig;
import com.thejobslk.entity.CurrentSession;
import com.thejobslk.entity.LoginDTO;
import com.thejobslk.entity.LoginUUIDKey;
import com.thejobslk.entity.User;
import com.thejobslk.exception.LoginException;
import com.thejobslk.repository.SessionDao;
import com.thejobslk.repository.UserDao;
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

		User existingUser = userDao.findByMobileNo(loginDTO.getMobileNo());

		if(existingUser == null) {
			throw new LoginException("Please enter valid Number " + loginDTO.getMobileNo());
		}

		Optional<CurrentSession> validCustomerSessionOpt =
				sessionDao.findById(existingUser.getUserId());


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
					existingUser.getPassword()) && existingUser.getMobileNo().equals(
					"1234567890")) {

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
			
			return "Logout successful";
			
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
