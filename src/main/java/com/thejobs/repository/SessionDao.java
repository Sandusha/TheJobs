package com.thejobs.repository;

import com.thejobs.entity.CurrentSession;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionDao extends JpaRepository<CurrentSession, Integer> {
	
	public CurrentSession findByUuid(String uuid);
	
}
