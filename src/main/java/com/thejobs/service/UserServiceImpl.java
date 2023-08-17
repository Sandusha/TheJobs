package com.thejobs.service;

import com.thejobs.entity.CurrentSession;
import com.thejobs.exception.LoginException;
import com.thejobs.repository.SessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    SessionDao sessionDao;
    @Override
    public CurrentSession getCurrentUserByUuid(String uuid) throws LoginException {

        CurrentSession currentUserSession = sessionDao.findByUuid(uuid);

        if(currentUserSession != null) {

            return currentUserSession;

        }else {

            throw new LoginException("Please enter valid key");
        }
    }

}
