package com.thejobs.service;


import com.thejobs.config.SpringdocConfig;
import com.thejobs.entity.Consultant;
import com.thejobs.entity.CurrentSession;
import com.thejobs.entity.LoginDTO;
import com.thejobs.entity.LoginUUIDKey;
import com.thejobs.exception.LoginException;
import com.thejobs.repository.ConsultantDao;
import com.thejobs.repository.SessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class ConsultantLoginServiceImpl implements ConsultantLoginService {
	
	@Autowired
	ConsultantDao consultantDao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException {
		
		LoginUUIDKey loginUUIDKey = new LoginUUIDKey();
		
		Consultant existingConsultant = consultantDao.findByEmail(loginDTO.getEmail());
		
		if(existingConsultant == null) {
			
			throw new LoginException("Please enter valid Email! " + loginDTO.getEmail());
			
		}
		
		
		
		// remove for  postman
		
	Optional<CurrentSession> validCustomerSessionOpt =
			sessionDao.findById(existingConsultant.getConsultantId());
		
		if(validCustomerSessionOpt.isPresent()) {

		throw new LoginException("Consultant already logged In!");

		}
		
		if(SpringdocConfig.bCryptPasswordEncoder.matches(loginDTO.getPassword(),
				existingConsultant.getPassword())) {
			
			// check Consultant have permission or not
			
		//	if(existingConsultant.getValidConsultant() == false) {
				
		//		throw new LoginException("No Permission. Please contact Admin" +
			//			" for permission.");
		//	}
		
//		if(existingConsultant.getPassword().equals(loginDTO.getPassword())) {
			
			String key = generateRandomString();
			
			CurrentSession currentUserSession =
					new CurrentSession(existingConsultant.getConsultantId(), key,
							LocalDateTime.now());



			existingConsultant.setType("Consultant");
			currentUserSession.setUserId(existingConsultant.getConsultantId());
			currentUserSession.setUserType("Consultant");
					
			consultantDao.save(existingConsultant);
			
			sessionDao.save(currentUserSession);
			
			loginUUIDKey.setMsg("Successfully logged in as Consultant with " +
					"the key");
			
			loginUUIDKey.setUuid(key);
			
			return loginUUIDKey;
		
		}else {
			
			throw new LoginException("Please enter valid password");
			
		}
	}
	
	

	@Override
	public String logoutFromAccount(String key) throws LoginException {
		
		
		
		CurrentSession currentConsultantOptional = sessionDao.findByUuid(key);
		
		if(currentConsultantOptional != null) {
			
			sessionDao.delete(currentConsultantOptional);
			
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
