package com.notification_svc.service;

import com.notification_svc.model.Notification;
import com.notification_svc.repository.NotificationRepository;
import com.notification_svc.web.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class NotificationService {

    private final JavaMailSender javaMailSender;
    private final NotificationRepository notificationRepository;

    public NotificationService(JavaMailSender javaMailSender, NotificationRepository notificationRepository) {
        this.javaMailSender = javaMailSender;
        this.notificationRepository = notificationRepository;
    }

    public void sendEmail(NotificationRequest notificationRequest) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationRequest.getEmail());
        message.setSubject(notificationRequest.getSubject());
        message.setText(notificationRequest.getBody());

        Notification notification = Notification.builder()
                .email(notificationRequest.getEmail())
                .subject(notificationRequest.getSubject())
                .body(notificationRequest.getBody())
                .createdOn(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }
}
