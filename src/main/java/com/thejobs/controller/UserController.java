package com.thejobs.controller;

import com.thejobs.entity.User;
import com.thejobs.exception.UserException;
import com.thejobs.service.ConsultantService;
import com.thejobs.service.UserAndAdminLoginService;
import com.thejobs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v3")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserAndAdminLoginService loginService;

    @Autowired
    ConsultantService consultantService;

    @CrossOrigin
    @PostMapping("/registerUser")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) throws UserException {

        User savedUser= userService.createUser(user);

        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);

    }









}
