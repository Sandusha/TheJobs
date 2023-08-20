package com.thejobslk.repository;


import com.thejobslk.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantDao extends JpaRepository<Consultant, Integer> {
	
	public Consultant findByMobileNo(String mobileNo);
}
