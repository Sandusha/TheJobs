package com.thejobslk.controller;
import com.thejobslk.entity.LoginDTO;
import com.thejobslk.entity.LoginResponse;
import com.thejobslk.exception.LoginException;
import com.thejobslk.entity.LoginUUIDKey;
import com.thejobslk.service.ConsultantLoginService;
import com.thejobslk.service.UserAndAdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class LoginController {
	
	@Autowired
	private UserAndAdminLoginService userAndAdminLoginService;
	
	@Autowired
	private ConsultantLoginService consultantLoginService;
	
	
	@PostMapping("/login")
	@CrossOrigin
	public ResponseEntity<LoginUUIDKey> loginUser(@RequestBody LoginDTO loginDTO) throws LoginException {
		
		LoginUUIDKey result = userAndAdminLoginService.logIntoAccount(loginDTO);
		
		
		return new ResponseEntity<LoginUUIDKey>(result,HttpStatus.OK);
		
	}
	
	@PostMapping("/loginConsultant")
	@CrossOrigin
	public ResponseEntity<LoginUUIDKey> loginConsultant(@RequestBody LoginDTO loginDTO) throws LoginException{
		
		LoginUUIDKey result = consultantLoginService.logIntoAccount(loginDTO);
		
		
		return new ResponseEntity<LoginUUIDKey>(result,HttpStatus.OK);
		
	}
	
	@CrossOrigin
	@GetMapping("/checkLogin/{uuid}")
	public ResponseEntity<LoginResponse> checkUserLoginORNot(@PathVariable String uuid) throws LoginException{
		
		Boolean loginResult = userAndAdminLoginService.checkUserLoginOrNot(uuid);
		
		LoginResponse loginResponse = new LoginResponse(loginResult);
		
		return new ResponseEntity<LoginResponse>(loginResponse,HttpStatus.OK);
		
	}
	
	@PostMapping("/logout")
	@CrossOrigin
	public String logoutUser(@RequestParam(required = false) String key) throws LoginException {
		
		return userAndAdminLoginService.logoutFromAccount(key);
		
	}
	
	@PostMapping("/logoutConsultant")
	@CrossOrigin
	public String logoutConsultant(@RequestParam(required = false) String key) throws LoginException {
		
		return consultantLoginService.logoutFromAccount(key);
		
	}

}






