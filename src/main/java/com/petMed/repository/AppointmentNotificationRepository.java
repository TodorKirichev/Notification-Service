package com.petMed.repository;

import com.petMed.model.AppointmentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentNotificationRepository extends JpaRepository<AppointmentNotification, UUID> {
}
