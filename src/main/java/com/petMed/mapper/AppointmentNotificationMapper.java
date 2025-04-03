package com.petMed.mapper;

import com.petMed.model.AppointmentNotification;
import com.petMed.web.dto.UpcomingAppointmentNotification;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class AppointmentNotificationMapper {

    public static UpcomingAppointmentNotification mapToUpcomingAppointmentNotification(AppointmentNotification appointmentNotification) {
        UpcomingAppointmentNotification upcomingAppointmentNotification = new UpcomingAppointmentNotification();
        upcomingAppointmentNotification.setPetName(appointmentNotification.getPetName());
        upcomingAppointmentNotification.setDate(appointmentNotification.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        upcomingAppointmentNotification.setTime(appointmentNotification.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        return upcomingAppointmentNotification;
    }
}
