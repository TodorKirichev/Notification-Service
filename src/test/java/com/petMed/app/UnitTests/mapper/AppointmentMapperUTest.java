package com.petMed.app.UnitTests.mapper;

import com.petMed.mapper.AppointmentNotificationMapper;
import com.petMed.model.AppointmentNotification;
import com.petMed.web.dto.UpcomingAppointmentNotification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AppointmentMapperUTest {

    @Test
    void mapToUpcomingAppointmentNotification_Success() {
        AppointmentNotification appointmentNotification = AppointmentNotification.builder()
                .petName("petName")
                .date(LocalDate.now())
                .time(LocalTime.now())
                .build();

        UpcomingAppointmentNotification upcomingAppointmentNotification = AppointmentNotificationMapper
                .mapToUpcomingAppointmentNotification(appointmentNotification);

        assertEquals(appointmentNotification.getPetName(), upcomingAppointmentNotification.getPetName());
        assertEquals(appointmentNotification.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), upcomingAppointmentNotification.getDate());
        assertEquals(appointmentNotification.getTime().format(DateTimeFormatter.ofPattern("HH:mm")), upcomingAppointmentNotification.getTime());

    }
}
