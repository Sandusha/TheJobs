package com.thejobs.service;

import com.thejobs.entity.CurrentSession;
import com.thejobs.entity.User;
import com.thejobs.exception.LoginException;
import com.thejobs.exception.UserException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
    User createUser(User user) throws UserException;

}
