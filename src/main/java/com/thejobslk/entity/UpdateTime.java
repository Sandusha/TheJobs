package com.thejobslk.entity;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateTime {
    private Integer appointmentFromTime;
    private Integer appointmentToTime;

}
