package com.notification_svc.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;
}
