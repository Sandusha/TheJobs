package com.thejobslk.repository;


import com.thejobslk.entity.CurrentSession;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionDao extends JpaRepository<CurrentSession, Integer> {
	
	public CurrentSession findByUuid(String uuid);
	
}
