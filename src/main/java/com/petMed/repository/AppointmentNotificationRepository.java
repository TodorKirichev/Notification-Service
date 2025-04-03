package com.petMed.repository;

import com.petMed.model.AppointmentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentNotificationRepository extends JpaRepository<AppointmentNotification, UUID> {

    @Query("select a from AppointmentNotification a where a.petOwnerUsername = :username and a.date = :today order by a.date desc , a.time asc limit 10")
    List<AppointmentNotification> findAllByPetOwnerUsernameOrderByDateDescTimeAsc(String username, LocalDate today);
}
