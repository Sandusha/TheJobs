package com.thejobslk.repository;

import com.thejobslk.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDao extends JpaRepository<Appointment, Integer> {

}
