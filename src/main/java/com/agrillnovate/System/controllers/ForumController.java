package com.agrillnovate.System.controllers;

import com.agrillnovate.System.dto.DTOConverter;
import com.agrillnovate.System.dto.PostDTO;
import com.agrillnovate.System.dto.ThreadDTO;
import com.agrillnovate.System.model.Thread;
import com.agrillnovate.System.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forums")
public class ForumController {

    private final ForumService forumService;

    @Autowired
    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @PostMapping("/threads")
    public ThreadDTO createThread(@RequestBody ThreadDTO threadDTO) {
        Thread thread = forumService.createThread(threadDTO);
        return DTOConverter.convertToThreadDTO(thread);
    }

    @PostMapping("/threads/{threadId}/posts")
    public PostDTO createPost(@PathVariable Long threadId, @RequestBody PostDTO postDTO) {
        return DTOConverter.convertToPostDTO(forumService.createPost(threadId, postDTO));
    }

    @GetMapping("/threads")
    public List<ThreadDTO> getAllThreads() {
        return forumService.getAllThreads().stream().map(DTOConverter::convertToThreadDTO).collect(Collectors.toList());
    }

    @GetMapping("/threads/{threadId}/posts")
    public List<PostDTO> getPostsByThreadId(@PathVariable Long threadId) {
        return forumService.getPostsByThreadId(threadId).stream().map(DTOConverter::convertToPostDTO).collect(Collectors.toList());
    }
}
