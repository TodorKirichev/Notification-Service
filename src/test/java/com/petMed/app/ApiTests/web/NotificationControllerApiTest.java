package com.petMed.app.ApiTests.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petMed.service.NotificationService;
import com.petMed.web.NotificationController;
import com.petMed.web.dto.AppointmentBookedRequest;
import com.petMed.web.dto.UpcomingAppointmentNotification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
public class NotificationControllerApiTest {

    @MockitoBean
    private NotificationService notificationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveAppointmentNotification_Success() throws Exception {

        AppointmentBookedRequest dto = new AppointmentBookedRequest();

        doNothing().when(notificationService).sendConfirmBookedAppointmentEmail(any());

        mockMvc.perform(post("/api/notifications/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).sendConfirmBookedAppointmentEmail(any());
    }

    @Test
    void get1AppointmentNotifications_Success() throws Exception {

        UpcomingAppointmentNotification upcomingAppointmentNotification = new UpcomingAppointmentNotification();

        when(notificationService.getUpcomingAppointmentNotifications(any(), any())).thenReturn(List.of(upcomingAppointmentNotification));

        mockMvc.perform(get("/api/notifications/appointments")
                        .param("username", "username"))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).getUpcomingAppointmentNotifications(any(), any());
    }

    @Test
    void closeNotification_Success() throws Exception {
        UUID uuid = UUID.randomUUID();

        doNothing().when(notificationService).changeStatusToSeen(any());

        mockMvc.perform(get("/api/notifications/close-notification")
                        .param("notificationId", uuid.toString()))
                .andExpect(status().isOk());
    }
}
