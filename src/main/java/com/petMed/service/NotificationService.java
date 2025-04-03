package com.petMed.service;

import com.petMed.email.EmailTemplates;
import com.petMed.mapper.AppointmentNotificationMapper;
import com.petMed.model.AppointmentNotification;
import com.petMed.model.UserRegisterNotification;
import com.petMed.repository.AppointmentNotificationRepository;
import com.petMed.web.dto.AppointmentBookedRequest;
import com.petMed.repository.UserRegisterNotificationRepository;
import com.petMed.event.payload.UserRegisterEvent;
import com.petMed.web.dto.UpcomingAppointmentNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class NotificationService {

    private final JavaMailSender javaMailSender;
    private final UserRegisterNotificationRepository userRegisterNotificationRepository;
    private final AppointmentNotificationRepository appointmentNotificationRepository;

    public NotificationService(JavaMailSender javaMailSender, UserRegisterNotificationRepository userRegisterNotificationRepository, AppointmentNotificationRepository appointmentNotificationRepository) {
        this.javaMailSender = javaMailSender;
        this.userRegisterNotificationRepository = userRegisterNotificationRepository;
        this.appointmentNotificationRepository = appointmentNotificationRepository;
    }

    @Transactional
    public void sendConfirmRegistrationEmail(UserRegisterEvent event) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject(EmailTemplates.CONFIRM_REGISTRATION_SUBJECT);
        message.setText(String.format(EmailTemplates.CONFIRM_REGISTRATION_BODY, event.getFirstName(), event.getLastName()));

        UserRegisterNotification userRegisterNotification = UserRegisterNotification.builder()
                .username(event.getUsername())
                .email(event.getEmail())
                .subject(message.getSubject())
                .body(message.getText())
                .createdOn(LocalDateTime.now())
                .build();

        userRegisterNotificationRepository.save(userRegisterNotification);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }

    @Transactional
    public void sendConfirmBookedAppointmentEmail(AppointmentBookedRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getPetOwnerEmail());
        message.setSubject(EmailTemplates.APPOINTMENT_BOOKED_SUBJECT);
        message.setText(String.format(EmailTemplates.APPOINTMENT_BOOKED_BODY,
                request.getVetFirstName(),
                request.getVetLastName(),
                request.getPetSpecies(),
                request.getPetName(),
                request.getDate(),
                request.getTime()));

        AppointmentNotification appointmentNotification = AppointmentNotification.builder()
                .petOwnerUsername(request.getPetOwnerUsername())
                .petOwnerEmail(request.getPetOwnerEmail())
                .petName(request.getPetName())
                .date(request.getDate())
                .time(request.getTime())
                .subject(message.getSubject())
                .body(message.getText())
                .createdOn(LocalDateTime.now())
                .build();

        appointmentNotificationRepository.save(appointmentNotification);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }

    public List<UpcomingAppointmentNotification> getUpcomingAppointmentNotifications(String username, LocalDate today) {
        List<UpcomingAppointmentNotification> upcomingNotifications = appointmentNotificationRepository.findAllByPetOwnerUsernameOrderByDateDescTimeAsc(username, today)
                .stream()
                .limit(10)
                .map(AppointmentNotificationMapper::mapToUpcomingAppointmentNotification)
                .toList();
        return upcomingNotifications;
    }
}
