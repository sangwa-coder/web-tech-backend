package com.agrillnovate.System.Listeners;

import com.agrillnovate.System.Events.NewPostEvent;
import com.agrillnovate.System.Events.NewThreadEvent;
import com.agrillnovate.System.dto.PostDTO;
import com.agrillnovate.System.dto.ThreadDTO;
import com.agrillnovate.System.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ForumEventListener {

    @Autowired
    private NotificationService notificationService;

    @EventListener
    public void handleNewThreadEvent(NewThreadEvent event) {
        ThreadDTO threadDTO = event.getThreadDTO();
        if (threadDTO == null) {
            System.err.println("ThreadDTO is null in NewThreadEvent.");
            return;
        }

        String title = threadDTO.getTitle();
        if (title == null || title.trim().isEmpty()) {
            System.err.println("Thread title is missing in ThreadDTO.");
            return;
        }

        Long userId = threadDTO.getUserId();
        if (userId == null) {
            System.err.println("User ID is null in ThreadDTO.");
            return;
        }

        String message = "New thread created: " + title;
        notificationService.sendNotification(message, userId);
    }

    @EventListener
    public void handleNewPostEvent(NewPostEvent event) {
        PostDTO postDTO = event.getPostDTO();
        if (postDTO == null) {
            System.err.println("PostDTO is null in NewPostEvent.");
            return;
        }

        Long threadId = postDTO.getThreadId();
        if (threadId == null) {
            System.err.println("Thread ID is missing in PostDTO.");
            return;
        }

        Long userId = postDTO.getUserId();
        if (userId == null) {
            System.err.println("User ID is null in PostDTO.");
            return;
        }

        String message = "New post created in thread: " + threadId;
        notificationService.sendNotification(message, userId);
    }
}
