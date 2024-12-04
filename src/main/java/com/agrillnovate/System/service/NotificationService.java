package com.agrillnovate.System.service;

import com.agrillnovate.System.model.Notification;
import com.agrillnovate.System.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Notification saveNotification(Notification notification) {
        notification.setTimestamp(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public void sendNotification(String message, Long userId) {
        Notification notification = new Notification(message, userId);
        saveNotification(notification);
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

}
