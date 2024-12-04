package com.agrillnovate.System.controllers;

import com.agrillnovate.System.dto.*;
import com.agrillnovate.System.model.*;
import com.agrillnovate.System.model.Thread;
import com.agrillnovate.System.security.jwt.JwtUtil;
import com.agrillnovate.System.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final ResearchService researchService;
    private final FeedbackService feedbackService;
    private final ForumService forumService;
    private final CommentService commentService;
    private final NotificationService notificationService;
    private final JwtUtil jwtUtil;
    @Autowired
    private PublicKnowledgeService publicKnowledgeService;

    @Autowired
    private InfographicService infographicService;
    @Autowired
    public AdminController(UserService userService, ResearchService researchService, FeedbackService feedbackService, ForumService forumService, NotificationService notificationService, JwtUtil jwtUtil, CommentService commentService) {
        this.userService = userService;
        this.researchService = researchService;
        this.feedbackService = feedbackService;
        this.forumService = forumService;
        this.notificationService = notificationService;
        this.jwtUtil = jwtUtil;
        this.commentService = commentService;
    }

    // User endpoints
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(user -> new UserDTO(
                        user.getUserID(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getAddress(),
                        user.getRole(),
                        user.getCreated_at(),
                        user.getEducationBackground(), // Include educationBackground
                        user.getCv(), // Include CV as a byte array
                        user.isApproved() // Include approved status
                ))
                .collect(Collectors.toList());
    }


    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Research endpoints
    @GetMapping("/research")
    public List<ResearchDTO> getAllResearch() {
        return researchService.getAllResearch();
    }

    @PostMapping(value = "/research", consumes = {"multipart/form-data"})
    public Research createResearch(
            @RequestPart("research") Research research,
            @RequestPart("images") MultipartFile[] images) throws IOException {
        return researchService.saveResearch(research, List.of(images));
    }

    @PutMapping(value = "/research/{id}", consumes = {"multipart/form-data"})
    public Research updateResearch(
            @PathVariable Long id,
            @RequestPart("research") Research researchDetails,
            @RequestPart("images") MultipartFile[] images) throws IOException {
        return researchService.updateResearch(id, researchDetails, List.of(images));
    }

    @GetMapping("/research/{id}")
    public ResearchDTO getResearchById(@PathVariable Long id) {
        return researchService.getResearchById(id);
    }

    @DeleteMapping("/research/{id}")
    public void deleteResearch(@PathVariable Long id) {
        researchService.deleteResearch(id);
    }

    // Feedback endpoints


    // Forum endpoints
    @GetMapping("/forums/threads")
    public List<ThreadDTO> getAllThreads() {
        return forumService.getAllThreads().stream()
                .map(DTOConverter::convertToThreadDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/forums/posts")
    public List<PostDTO> getAllPosts() {
        return forumService.getAllPosts().stream()
                .map(DTOConverter::convertToPostDTO)
                .collect(Collectors.toList());
    }

    // Notifications endpoint
//threads

    @PostMapping("/forums/threads")
    public ThreadDTO createThread(@RequestBody ThreadDTO threadDTO, @RequestHeader("Authorization") String token) {
        Long userId = Long.valueOf(jwtUtil.extractUserId(token.replace("Bearer ", "")));
        threadDTO.setUserId(userId); // Set the user ID in the ThreadDTO
        Thread thread = forumService.createThread(threadDTO);
        return DTOConverter.convertToThreadDTO(thread);
    }

    @PutMapping("/forums/threads/{id}")
    public ThreadDTO updateThread(@PathVariable Long id, @RequestBody ThreadDTO threadDTO) {
        return DTOConverter.convertToThreadDTO(forumService.updateThread(id, threadDTO));
    }

    @DeleteMapping("/forums/threads/{threadId}")
    public ResponseEntity<Void> deleteThread(@PathVariable Long threadId) {
        forumService.deleteThread(threadId);
        return ResponseEntity.noContent().build();
    }

    // Post CRUD endpoints

    @GetMapping("/forums/threads/{threadId}/posts")
    public List<PostDTO> getPostsByThreadId(@PathVariable Long threadId) {
        return forumService.getPostsByThreadId(threadId).stream()
                .map(DTOConverter::convertToPostDTO)
                .collect(Collectors.toList());
    }


    @PostMapping("/forums/threads/{threadId}/posts")
    public PostDTO createPost(@PathVariable Long threadId, @RequestBody PostDTO postDTO) {
        return DTOConverter.convertToPostDTO(forumService.createPost(threadId, postDTO));
    }

    @PutMapping("/forums/posts/{id}")
    public PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        return DTOConverter.convertToPostDTO(forumService.updatePost(id, postDTO));
    }

    @DeleteMapping("/forums/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        forumService.deletePost(id);
    }

    @GetMapping("/feedback")
    public List<FeedbackDTO> getAllFeedback() {
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        return feedbacks.stream()
                .map(feedback -> new FeedbackDTO(
                        feedback.getFeedbackID(),
                        feedback.getContent(),
                        feedback.getDateSubmitted(),
                        feedback.getResearch().getResearchID()
                ))
                .collect(Collectors.toList());
    }

    // Comment endpoints
    @GetMapping("/comments")
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return comments.stream()
                .map(comment -> new CommentDTO(
                        comment.getResearch().getResearchID(),
                        comment.getName(),
                        comment.getContent(),
                        comment.getEmail(),
                        comment.getPhone(),
                        comment.getDateSubmitted()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping("/comments")
    public CommentDTO createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.createComment(commentDTO);
        return new CommentDTO(
                comment.getResearch().getResearchID(),
                comment.getName(),
                comment.getContent(),
                comment.getEmail(),
                comment.getPhone(),
                comment.getDateSubmitted()
        );
    }

    @PostMapping("/feedback")
    public FeedbackDTO createFeedback(@RequestParam Long researchID, @RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.createFeedback(researchID, feedbackDTO);
        return new FeedbackDTO(
                feedback.getFeedbackID(),
                feedback.getContent(),
                feedback.getDateSubmitted(),
                feedback.getResearch().getResearchID()
        );
    }
    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
    // Public Knowledge endpoints
    @GetMapping("/public-knowledge")
    public List<PublicKnowledgeDTO> getAllPublicKnowledge() {
        return publicKnowledgeService.getAllPublicKnowledge();
    }

    @PostMapping("/public-knowledge")
    public PublicKnowledgeDTO createPublicKnowledge(@RequestParam("title") String title,
                                                    @RequestParam("content") String content,
                                                    @RequestParam("image") MultipartFile image,
                                                    @RequestParam("datePublished") String datePublished) throws IOException, ParseException {
        PublicKnowledgeDTO publicKnowledgeDTO = new PublicKnowledgeDTO();
        publicKnowledgeDTO.setTitle(title);
        publicKnowledgeDTO.setContent(content);
        publicKnowledgeDTO.setImage(image.getBytes());
        publicKnowledgeDTO.setDatePublished(convertToTimestamp(datePublished));
        return publicKnowledgeService.createPublicKnowledge(publicKnowledgeDTO);
    }

    @PutMapping("/public-knowledge/{id}")
    public PublicKnowledgeDTO updatePublicKnowledge(@PathVariable Long id,
                                                    @RequestParam("title") String title,
                                                    @RequestParam("content") String content,
                                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                                    @RequestParam("datePublished") String datePublished) throws IOException, ParseException {
        PublicKnowledgeDTO publicKnowledgeDTO = new PublicKnowledgeDTO();
        publicKnowledgeDTO.setTitle(title);
        publicKnowledgeDTO.setContent(content);
        if (image != null) {
            publicKnowledgeDTO.setImage(image.getBytes());
        }
        publicKnowledgeDTO.setDatePublished(convertToTimestamp(datePublished));
        return publicKnowledgeService.updatePublicKnowledge(id, publicKnowledgeDTO);
    }


    @DeleteMapping("/public-knowledge/{id}")
    public ResponseEntity<Void> deletePublicKnowledge(@PathVariable Long id) {
        publicKnowledgeService.deletePublicKnowledge(id);
        return ResponseEntity.noContent().build();
    }

    private Timestamp convertToTimestamp(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        java.util.Date parsedDate = dateFormat.parse(date);
        return new Timestamp(parsedDate.getTime());
    }



    // Infographic endpoints

    @GetMapping("/infographics")
    public List<InfographicDTO> getAllInfographics() {
        return infographicService.getAllInfographics();
    }

    @PostMapping("/infographics")
    public InfographicDTO createInfographic(@RequestParam("title") String title,
                                            @RequestParam("description") String description,
                                            @RequestParam("image") MultipartFile image) throws IOException {
        InfographicDTO infographicDTO = new InfographicDTO();
        infographicDTO.setTitle(title);
        infographicDTO.setDescription(description);
        infographicDTO.setImage(image.getBytes());
        return infographicService.createInfographic(infographicDTO);
    }

    @PutMapping("/infographics/{id}")
    public InfographicDTO updateInfographic(@PathVariable Long id,
                                            @RequestParam("title") String title,
                                            @RequestParam("description") String description,
                                            @RequestParam("image") MultipartFile image) throws IOException {
        InfographicDTO infographicDTO = new InfographicDTO();
        infographicDTO.setTitle(title);
        infographicDTO.setDescription(description);
        infographicDTO.setImage(image.getBytes());
        return infographicService.updateInfographic(id, infographicDTO);
    }

    @DeleteMapping("/infographics/{id}")
    public ResponseEntity<Void> deleteInfographic(@PathVariable Long id) {
        infographicService.deleteInfographic(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/notifications")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }
    // Add this method to AdminController
    @GetMapping("/reports")
    public SystemReportDTO getSystemReport() {
        SystemReportDTO report = new SystemReportDTO();
        report.setTotalUsers(userService.countUsers());
        report.setTotalResearch(researchService.countResearch());
        report.setTotalComments(commentService.countComments());
        report.setTotalThreads(forumService.countThreads());
        report.setTotalPosts(forumService.countPosts());
        report.setTotalFeedback(feedbackService.countFeedback());

        report.setUsers(userService.getAllUsers());
        report.setResearch(researchService.getAllResearch());
        report.setComments(commentService.getAllComments());
        report.setThreads(forumService.getAllThreads());
        report.setPosts(forumService.getAllPosts());
        report.setFeedback(feedbackService.getAllFeedback());

        return report;
    }

    // Add this method to AdminController
    @GetMapping("/expert-research/{expertId}")
    public List<ResearchDTO> getResearchByExpert(@PathVariable Long expertId) {
        List<Research> researches = researchService.getResearchByUserId(expertId);
        return researches.stream()
                .map(research -> new ResearchDTO(
                        research.getResearchID(),
                        research.getTitle(),
                        research.getAuthor(),
                        research.getDatePublished(),
                        research.getContent(),
                        research.getStatus(),
                        research.getLatitude(),
                        research.getLongitude(),
                        research.getCategory(),
                        research.getImages().stream()
                                .map(image -> new ResearchImageDTO(image.getId(), image.getImage()))
                                .collect(Collectors.toList()),
                        research.getFeedbacks().stream()
                                .map(feedback -> new FeedbackDTO(feedback.getFeedbackID(), feedback.getContent(), feedback.getDateSubmitted(), feedback.getResearch().getResearchID()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }





    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveUser(@PathVariable Long id) {
        try {
            User user = userService.approveUser(id);
            return ResponseEntity.ok("User approved successfully.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }


    @PostMapping("/disable/{id}")
    public ResponseEntity<String> disableUser(@PathVariable Long id) {
        try {
            User user = userService.disableUser(id);
            return ResponseEntity.ok("User disabled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}