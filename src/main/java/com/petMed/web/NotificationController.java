package com.petMed.web;

import com.petMed.service.NotificationService;
import com.petMed.web.dto.AppointmentBookedRequest;
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

    @PostMapping("/appointments")
    public ResponseEntity<Void> saveAppointmentNotification(@RequestBody AppointmentBookedRequest request) {
        notificationService.sendConfirmBookedAppointmentEmail(request);
        return ResponseEntity.ok().build();
    }


}
