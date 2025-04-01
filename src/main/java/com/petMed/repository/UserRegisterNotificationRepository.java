package com.petMed.repository;

import com.petMed.model.UserRegisterNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRegisterNotificationRepository extends JpaRepository<UserRegisterNotification, UUID> {
}
