package com.thejobs.entity;


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
	//not sure yet we'll see
	

	private LocalDateTime appointmentDateAndTime;
	
	
	@ManyToOne
	private Consultant consultant;
	



	@Override
	public int hashCode() {
		return Objects.hash(appointmentId);
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			
			return true;
		
		if (obj == null)
			
			return false;
		
		if (getClass() != obj.getClass())
			
			return false;
		
		Appointment other = (Appointment) obj;
		
		return Objects.equals(appointmentId, other.appointmentId);

	}
	
	

}

