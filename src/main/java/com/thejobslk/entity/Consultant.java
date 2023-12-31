package com.thejobslk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Consultant {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "id_table"
	)

	@SequenceGenerator(
			name = "id_table",
			sequenceName = "id_sequence",
			allocationSize = 1
	)
	private Integer consultantId;
	private String name;
	private String email;
	private String password;
	@Pattern(regexp = "^[0-9]{10}$", message = "Please enter valid mobile number")
	private String mobileNo;
	private String specialty;
	private String experience;
	//	@OneToMany(cascade = CascadeType.ALL,mappedBy = "appointmentId") from
	//	tutorial
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();
	private Integer appointmentFromTime;
	private Integer appointmentToTime;
	private Boolean validConsultant = true;
	private String type;

}















