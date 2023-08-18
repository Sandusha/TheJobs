package com.thejobs.service;

import com.thejobs.entity.CurrentSession;
import com.thejobs.entity.User;
import com.thejobs.exception.LoginException;
import com.thejobs.exception.UserException;
import com.thejobs.repository.SessionDao;
import com.thejobs.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.thejobs.config.SpringdocConfig.bCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    SessionDao sessionDao;
    @Autowired
    UserDao userDao;



    @Override
    public CurrentSession getCurrentUserByUuid(String uuid) throws LoginException {
     CurrentSession currentUserSession = sessionDao.findByUuid(uuid);
     if(currentUserSession != null) {
      return currentUserSession;}
     else{
         throw new LoginException("Please enter valid key");
        }
    }

    @Override
    public User createUser(User user) throws UserException {

        User databaseUser = userDao.findByEmail(user.getEmail());
        if(databaseUser == null) {
        user.setType("User");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return user;
        }else
        {
            throw new UserException("User already registered with this Email" +
                    " Id " + user.getEmail());
             }
    }




}
