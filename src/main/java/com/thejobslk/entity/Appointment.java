package com.thejobslk.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
@ToString
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer appointmentId;
	@ManyToOne
	private User user;
	// Appointement default time will be 60 mins from appoaintment start time.
	private LocalDateTime appointmentDateAndTime;
	@ManyToOne
	private Consultant consultant;

	}





