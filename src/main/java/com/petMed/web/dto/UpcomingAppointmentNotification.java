package com.petMed.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingAppointmentNotification {

    private String petName;
    private String date;
    private String time;

}
