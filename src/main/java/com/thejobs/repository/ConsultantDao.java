package com.thejobs.repository;

import com.thejobs.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantDao extends JpaRepository<Consultant, String> {
	
	public Consultant findByEmail(String email);
}
