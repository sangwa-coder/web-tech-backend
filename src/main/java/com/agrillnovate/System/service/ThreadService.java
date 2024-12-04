package com.agrillnovate.System.service;

import com.agrillnovate.System.dto.ThreadDTO;
import com.agrillnovate.System.model.Thread;
import com.agrillnovate.System.model.Post;
import com.agrillnovate.System.Repository.ThreadRepository;
import com.agrillnovate.System.Repository.PostRepository;
import com.agrillnovate.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Thread createThread(ThreadDTO threadDTO) {
        Thread thread = new Thread();
        thread.setTitle(threadDTO.getTitle());
        thread.setUser(userRepository.findById(threadDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        return threadRepository.save(thread);
    }

    public List<Post> getPosts(Long threadId) {
        return postRepository.findByThreadThreadId(threadId);
    }

}
