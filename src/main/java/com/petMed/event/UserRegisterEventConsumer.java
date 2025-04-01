package com.petMed.event;

import com.petMed.service.NotificationService;
import com.petMed.event.payload.UserRegisterEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterEventConsumer {

    private final NotificationService notificationService;

    public UserRegisterEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "user-register-event", groupId = "notification-svc-group")
    public void consumeEvent(UserRegisterEvent event) {
        notificationService.sendConfirmRegistrationEmail(event);
    }
}
