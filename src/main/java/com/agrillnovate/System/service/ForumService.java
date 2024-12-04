package com.agrillnovate.System.service;

import com.agrillnovate.System.dto.PostDTO;
import com.agrillnovate.System.dto.ThreadDTO;
import com.agrillnovate.System.Events.NewPostEvent;
import com.agrillnovate.System.Events.NewThreadEvent;
import com.agrillnovate.System.model.Post;
import com.agrillnovate.System.model.Thread;
import com.agrillnovate.System.model.User;
import com.agrillnovate.System.Repository.PostRepository;
import com.agrillnovate.System.Repository.ThreadRepository;
import com.agrillnovate.System.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumService {

    private final ThreadRepository threadRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ForumService(ThreadRepository threadRepository, PostRepository postRepository, UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.threadRepository = threadRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public Thread createThread(ThreadDTO threadDTO) {
        User user = userRepository.findById(threadDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Thread thread = new Thread();
        thread.setTitle(threadDTO.getTitle());
        thread.setCreated_at(LocalDateTime.now());
        thread.setUser(user);
        Thread savedThread = threadRepository.save(thread);

        // Publish the event
        eventPublisher.publishEvent(new NewThreadEvent(this, threadDTO));

        return savedThread;
    }

    public Post createPost(Long threadId, PostDTO postDTO) {
        Thread thread = threadRepository.findById(threadId).orElseThrow(() -> new RuntimeException("Thread not found"));
        User user = userRepository.findById(postDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setCreated_at(LocalDateTime.now());
        post.setThread(thread);
        post.setUser(user);
        Post savedPost = postRepository.save(post);

        // Publish the event
        eventPublisher.publishEvent(new NewPostEvent(this, postDTO));

        return savedPost;
    }

    public List<Thread> getAllThreads() {
        return threadRepository.findAll();
    }

    public List<Post> getPostsByThreadId(Long threadId) {
        return postRepository.findByThreadThreadId(threadId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Thread updateThread(Long id, ThreadDTO threadDTO) {
        Thread thread = threadRepository.findById(id).orElseThrow(() -> new RuntimeException("Thread not found"));
        thread.setTitle(threadDTO.getTitle());
        return threadRepository.save(thread);
    }


    @Transactional
    public void deleteThread(Long threadId) {
        threadRepository.deleteById(threadId);
    }




    public Post updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setContent(postDTO.getContent());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }


    public long countThreads() {
        return threadRepository.count();
    }
    public long countPosts() {
        return postRepository.count();
    }
}
