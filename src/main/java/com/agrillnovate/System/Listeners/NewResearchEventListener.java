package com.agrillnovate.System.Listeners;

import com.agrillnovate.System.Events.NewResearchEvent;
import com.agrillnovate.System.model.Notification;
import com.agrillnovate.System.model.Research;
import com.agrillnovate.System.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewResearchEventListener implements ApplicationListener<NewResearchEvent> {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(NewResearchEvent event) {
        Research research = event.getResearch();
        String message = "New research submitted: " + research.getTitle();

        // Create and save notification
        Notification notification = new Notification(message, research.getUser().getUserID());
        notificationService.saveNotification(notification);

        // Send notification to WebSocket
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}