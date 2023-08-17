package com.thejobs.service;

import com.thejobs.entity.Consultant;
import com.thejobs.exception.ConsultantException;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    Consultant registerConsultant (Consultant consultant) throws ConsultantException;




}
