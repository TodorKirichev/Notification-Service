package com.petMed.app.UnitTests.service;

import com.petMed.event.payload.UserRegisterEvent;
import com.petMed.model.AppointmentNotification;
import com.petMed.model.NotificationStatus;
import com.petMed.repository.AppointmentNotificationRepository;
import com.petMed.repository.UserRegisterNotificationRepository;
import com.petMed.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceUTest {

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private UserRegisterNotificationRepository userRegisterNotificationRepository;
    @Mock
    private AppointmentNotificationRepository appointmentNotificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void sendConfirmRegistrationEmail_Success() {
        UserRegisterEvent event = new UserRegisterEvent();
        event.setUsername("username");
        event.setEmail("test@test.com");
        event.setFirstName("firstName");
        event.setLastName("lastName");

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        notificationService.sendConfirmRegistrationEmail(event);

        verify(userRegisterNotificationRepository, times(1)).save(any());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEmail_ShouldHandleException_WhenConfirmRegistrationEmailSendingFails() {
        doThrow(new MailSendException("Mail error")).when(javaMailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() -> notificationService.sendConfirmRegistrationEmail(new UserRegisterEvent()));

        verify(userRegisterNotificationRepository, times(1)).save(any());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void findById_Success() {
        UUID id = UUID.randomUUID();
        AppointmentNotification appointmentNotification = AppointmentNotification.builder()
                .id(id)
                .petOwnerUsername("petOwnerUsername")
                .build();

        when(appointmentNotificationRepository.findById(any())).thenReturn(Optional.of(appointmentNotification));

        AppointmentNotification result = notificationService.findById(id);

        assertEquals(appointmentNotification.getId(), result.getId());
        assertEquals(appointmentNotification.getPetOwnerUsername(), result.getPetOwnerUsername());
    }

    @Test
    void findById_ShouldThrow_WhenNotFound() {
        when(appointmentNotificationRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> notificationService.findById(any()));
    }

    @Test
    void changeStatusToSeen_Success() {
        UUID id = UUID.randomUUID();
        AppointmentNotification appointmentNotification = AppointmentNotification.builder()
                .id(id)
                .status(NotificationStatus.NOT_SEEN)
                .build();

        when(appointmentNotificationRepository.findById(any())).thenReturn(Optional.of(appointmentNotification));

        notificationService.changeStatusToSeen(id);

        assertEquals(NotificationStatus.SEEN, appointmentNotification.getStatus());
    }
}
