package com.thejobs.controller;

import com.thejobs.entity.Consultant;
import com.thejobs.entity.CurrentSession;
import com.thejobs.exception.ConsultantException;
import com.thejobs.exception.LoginException;
import com.thejobs.service.AdminService;
import com.thejobs.service.UserAndAdminLoginService;
import com.thejobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserAndAdminLoginService userAndAdminLoginService;

    @Autowired
    UserService userService;


    @PostMapping(path ="/registerConsultant")
    @CrossOrigin
    public ResponseEntity<Consultant> registerConsultant(@RequestParam String key,
                                                         @RequestBody Consultant consultant) throws ConsultantException, LoginException {

        if (userAndAdminLoginService.checkUserLoginOrNot(key)) {

            CurrentSession currentUserSession =
                    userService.getCurrentUserByUuid(key);

            if (!currentUserSession.getUserType().equals("admin")) {

                throw new LoginException("Please login as admin");

            }

            if (consultant != null) {

                Consultant registerConsultant =
                        adminService.registerConsultant(consultant);

                return new ResponseEntity<Consultant>(registerConsultant, HttpStatus.CREATED);

            } else {

                throw new ConsultantException("Please enter Valid Details");
            }

        } else {

            throw new LoginException("Key Miss match! Please enter valid key.");
        }


    }
}
