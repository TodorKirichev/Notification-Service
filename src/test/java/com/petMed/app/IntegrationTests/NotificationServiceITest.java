package com.petMed.app.IntegrationTests;

import com.petMed.repository.AppointmentNotificationRepository;
import com.petMed.service.NotificationService;
import com.petMed.web.dto.AppointmentBookedRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NotificationServiceITest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AppointmentNotificationRepository appointmentNotificationRepository;

    @MockitoBean
    private JavaMailSender mailSender;

    @Test
    void sendConfirmBookedAppointmentEmail_Success() {
        AppointmentBookedRequest request = new AppointmentBookedRequest();
        request.setPetOwnerUsername("username");
        request.setPetOwnerEmail("username@petmed.com");
        request.setVetFirstName("firstName");
        request.setVetLastName("lastName");
        request.setDate(LocalDate.now());
        request.setTime(LocalTime.now());
        request.setPetName("petName");
        request.setPetSpecies("species");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getPetOwnerEmail());
        message.setSubject("subject");
        message.setText("text");

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        notificationService.sendConfirmBookedAppointmentEmail(request);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));

    }
}
