package com.notification_svc.service;

import com.notification_svc.repository.NotificationRepository;
import com.notification_svc.web.dto.NotificationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceUTest {

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void sendEmail_Success() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEmail("test@test.com");
        notificationRequest.setSubject("subject");
        notificationRequest.setBody("body");

        notificationService.sendEmail(notificationRequest);

        verify(notificationRepository, times(1)).save(any());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEmail_ShouldHandleException_WhenEmailSendingFails() {
        doThrow(new MailSendException("Mail error")).when(javaMailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() -> notificationService.sendEmail(new NotificationRequest()));

        verify(notificationRepository, times(1)).save(any());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
