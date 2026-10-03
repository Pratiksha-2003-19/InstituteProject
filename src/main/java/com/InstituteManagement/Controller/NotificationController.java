package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.Notification;
import com.InstituteManagement.Service.NotificationService;
import com.InstituteManagement.dto.CreateNotificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/notifications")
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody CreateNotificationRequest request) {
        return ResponseEntity.ok(notificationService.createNotification(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TRAINER')")
    @GetMapping("/admin/notifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TRAINER')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TRAINER')")
    @PutMapping("/notifications/{notificationId}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long notificationId) {
        return ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }
}
