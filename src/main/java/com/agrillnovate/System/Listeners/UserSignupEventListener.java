package com.agrillnovate.System.Listeners;

import com.agrillnovate.System.Events.UserSignupEvent;
import com.agrillnovate.System.model.Notification;
import com.agrillnovate.System.model.User;
import com.agrillnovate.System.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserSignupEventListener implements ApplicationListener<UserSignupEvent> {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(UserSignupEvent event) {
        User user = event.getUser();
        String message = "New user signed up: " + user.getName();

        // Create and save notification
        Notification notification = new Notification(message, user.getUserID());
        notificationService.saveNotification(notification);

        // Send notification to WebSocket
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}