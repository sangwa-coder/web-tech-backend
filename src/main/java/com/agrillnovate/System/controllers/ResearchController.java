package com.agrillnovate.System.controllers;

import com.agrillnovate.System.Events.NewResearchEvent;
import com.agrillnovate.System.Repository.ResearchImageRepository;
import com.agrillnovate.System.Repository.ResearchRepository;
import com.agrillnovate.System.dto.*;
import com.agrillnovate.System.model.*;
import com.agrillnovate.System.service.CommentService;
import com.agrillnovate.System.service.FeedbackService;
import com.agrillnovate.System.service.ResearchService;
import com.agrillnovate.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/research")
public class ResearchController {

    @Autowired
    private ResearchService researchService;
    @Autowired
    private UserService userService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ResearchImageRepository researchImageRepository;
    @Autowired
    private ResearchRepository researchRepository;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Research submitResearch(
            @RequestPart("research") Research research,
            @RequestPart("images") MultipartFile[] images,
            @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        User user = userService.findByEmail(userDetails.getUsername());
        research.setUser(user);
        Research savedResearch = researchService.saveResearch(research, List.of(images));

        // Send notification to WebSocket
        String notificationMessage = "New research submitted by " + user.getName() + ": " + research.getTitle();
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);

        // Publish event
        eventPublisher.publishEvent(new NewResearchEvent(this, savedResearch));

        return savedResearch;
    }

    @GetMapping("/{id}")
    public ResearchDTO getResearchById(@PathVariable Long id) {
        return researchService.getResearchById(id);
    }

    @GetMapping
    public List<ResearchDTO> getAllResearch() {
        return researchService.getAllResearch();
    }

    @PutMapping("/{id}")
    public Research updateResearch(
            @PathVariable Long id,
            @RequestPart("research") Research researchDetails,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {

        return researchService.updateResearch(id, researchDetails, images != null ? List.of(images) : null);
    }


    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ResearchDTO research = researchService.getResearchById(id);
        if (research.getImages() != null && !research.getImages().isEmpty()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.jpg\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(research.getImages().get(0).getImage()); // Assuming we are getting the first image
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void deleteResearch(@PathVariable Long id) {
        researchService.deleteResearch(id);
    }

    @GetMapping("/user/{userId}")
    public List<ResearchDTO> getResearchByUserId(@PathVariable Long userId) {
        List<Research> researches = researchService.getResearchByUserId(userId);
        return researches.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ResearchDTO convertToDTO(Research research) {
        return new ResearchDTO(
                research.getResearchID(),
                research.getTitle(),
                research.getAuthor(),
                research.getDatePublished(),
                research.getContent(),
                research.getStatus(),
                research.getLatitude(),
                research.getLongitude(),
                research.getCategory(), // Add this line
                research.getImages().stream().map(img -> new ResearchImageDTO(img.getId(), img.getImage())).collect(Collectors.toList()),
                research.getFeedbacks().stream().map(feedback -> new FeedbackDTO(feedback.getFeedbackID(), feedback.getContent(), feedback.getDateSubmitted(), feedback.getResearch().getResearchID())).collect(Collectors.toList())
        );
    }


    // New methods for feedback and comments

    // New methods for feedback and comments
    @GetMapping("/{id}/comments")
    public List<CommentDTO> getCommentsByResearchId(@PathVariable Long id) {
        return researchService.getCommentsByResearchID(id);
    }

    @GetMapping("/{id}/feedbacks")
    public List<FeedbackDTO> getFeedbackByResearchId(@PathVariable Long id) {
        return researchService.getFeedbackByResearchID(id);
    }

    @GetMapping("/comments/stats")
    public List<CommentStat> getCommentStats() {
        return commentService.getCommentStats();
    }

    @GetMapping("/feedbacks/stats")
    public List<FeedbackStat> getFeedbackStats() {
        return feedbackService.getFeedbackStats();
    }

    // Fetch distinct categories
    @GetMapping("/categories")
    public List<String> getCategories() {
        return researchService.getDistinctCategories();
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteResearchImage(@PathVariable Long imageId) {
        Optional<ResearchImage> optionalImage = researchImageRepository.findById(imageId);
        if (optionalImage.isPresent()) {
            ResearchImage image = optionalImage.get();
            Research research = image.getResearch();

            // Remove image from research entity and delete
            research.getImages().remove(image);
            researchImageRepository.delete(image);
            researchRepository.save(research);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
