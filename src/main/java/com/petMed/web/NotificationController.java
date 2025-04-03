package com.petMed.web;

import com.petMed.model.AppointmentNotification;
import com.petMed.service.NotificationService;
import com.petMed.web.dto.AppointmentBookedRequest;
import com.petMed.web.dto.UpcomingAppointmentNotification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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


}
