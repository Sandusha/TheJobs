package com.thejobslk.controller;
import com.thejobslk.service.ConsultantLoginService;
import com.thejobslk.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v3")
public class ConsultantController {
	
	@Autowired
	ConsultantLoginService consultantLoginService;
	
	@Autowired
	ConsultantService consultantService;
	

	

}


























