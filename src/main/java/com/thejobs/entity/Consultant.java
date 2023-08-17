package com.thejobs.entity;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    // put time only 24 hours front ek ghddi

    //	@Column(name = "column mark krnn")
    private Integer appointmentFromTime;

    // put time only 24 hours format front ekedi

    //	@Column(name hariyt dnn)
    private Integer appointmentToTime;

    // for checking this is Consultant  or user
    private String type;





}
