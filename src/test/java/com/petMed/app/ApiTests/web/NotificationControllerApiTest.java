package com.petMed.app.ApiTests.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petMed.service.NotificationService;
import com.petMed.web.NotificationController;
import com.petMed.web.dto.AppointmentBookedRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
}
