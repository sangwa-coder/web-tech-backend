package com.agrillnovate.System.Listeners;

import com.agrillnovate.System.Events.FeedbackEvent;
import com.agrillnovate.System.model.Feedback;
import com.agrillnovate.System.model.Notification;
import com.agrillnovate.System.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeedbackEventListener implements ApplicationListener<FeedbackEvent> {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(FeedbackEvent event) {
        Feedback feedback = event.getFeedback();
        String message = "New feedback received: " + feedback.getContent();

        // Create and save notification
        Notification notification = new Notification(message, feedback.getResearch().getResearchID());
        notificationService.saveNotification(notification);

        // Send notification to WebSocket
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
