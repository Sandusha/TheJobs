package com.thejobslk.repository;


import com.thejobslk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserDao extends JpaRepository<User, Integer> {
	
	public User findByMobileNo(String mobileNo);
}
