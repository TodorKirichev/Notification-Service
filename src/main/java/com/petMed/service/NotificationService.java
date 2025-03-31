package com.petMed.service;

import com.petMed.model.Notification;
import com.petMed.repository.NotificationRepository;
import com.petMed.event.payload.UserRegisterEvent;
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

    public void sendEmail(UserRegisterEvent event) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject(event.getEmailSubject());
        message.setText(event.getEmailBody());

        Notification notification = Notification.builder()
                .username(event.getUsername())
                .email(event.getEmail())
                .subject(event.getEmailSubject())
                .body(event.getEmailBody())
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
