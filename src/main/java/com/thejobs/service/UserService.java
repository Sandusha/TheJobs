package com.thejobs.service;

import com.thejobs.entity.CurrentSession;
import com.thejobs.exception.LoginException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
}
