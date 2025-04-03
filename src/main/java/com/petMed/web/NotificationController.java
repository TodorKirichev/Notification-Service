package com.petMed.web;

import com.petMed.service.NotificationService;
import com.petMed.web.dto.AppointmentBookedRequest;
import com.petMed.web.dto.UpcomingAppointmentNotification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:8080")
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

    @GetMapping("/appointments")
    public ResponseEntity<List<UpcomingAppointmentNotification>> get1AppointmentNotifications(@RequestParam(required = true) String username) {
        List<UpcomingAppointmentNotification> upcomingAppointmentNotifications = notificationService.getUpcomingAppointmentNotifications(username, LocalDate.now());
        return ResponseEntity.ok(upcomingAppointmentNotifications);
    }

    @GetMapping("/close-notification")
    public ResponseEntity<Void> closeNotification(@RequestParam UUID notificationId) {
        notificationService.changeStatusToSeen(notificationId);
        return ResponseEntity.ok().build();
    }


}
