package com.notification_svc.web;

import com.notification_svc.service.NotificationService;
import com.notification_svc.web.dto.NotificationRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody NotificationRequest notificationRequest) {

        notificationService.sendEmail(notificationRequest);

        return ResponseEntity.ok().build();
    }
}
