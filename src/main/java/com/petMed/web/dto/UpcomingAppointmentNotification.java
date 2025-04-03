package com.petMed.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingAppointmentNotification {

    private UUID notificationId;
    private String petName;
    private String date;
    private String time;

}
