package com.agrillnovate.System.controllers;

import com.agrillnovate.System.dto.DTOConverter;
import com.agrillnovate.System.dto.PostDTO;
import com.agrillnovate.System.dto.ThreadDTO;
import com.agrillnovate.System.model.Notification;
import com.agrillnovate.System.service.ForumService;
import com.agrillnovate.System.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);


    private final ForumService forumService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    public WebSocketController(ForumService forumService) {
        this.forumService = forumService;
    }

    @MessageMapping("/forum/{threadId}/newPost")
    @SendTo("/topic/thread/{threadId}")
    public PostDTO newPost(@Payload PostDTO postDTO, @DestinationVariable Long threadId) {
        return DTOConverter.convertToPostDTO(forumService.createPost(threadId, postDTO));
    }

    @MessageMapping("/forum/getThreads")
    @SendTo("/topic/threads")
    public List<ThreadDTO> getThreads() {
        return forumService.getAllThreads().stream().map(DTOConverter::convertToThreadDTO).collect(Collectors.toList());
    }

    @MessageMapping("/forum/{threadId}/getPosts")
    @SendTo("/topic/thread/{threadId}")
    public List<PostDTO> getPosts(@DestinationVariable Long threadId) {
        return forumService.getPostsByThreadId(threadId).stream().map(DTOConverter::convertToPostDTO).collect(Collectors.toList());
    }

    @MessageMapping("/new-research")
    @SendTo("/topic/research")
    public String broadcastNewResearch(String message) {
        return message;
    }

    @MessageMapping("/forums")
    @SendTo("/topic/forums")
    public String broadcastMessage(String message) {
        return message;
    }


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        return "Hello, " + message + "!";
    }
}
