package com.thejobs.repository;


import com.thejobs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserDao extends JpaRepository<User, String> {
	
	public User findByEmail(String email);
}
