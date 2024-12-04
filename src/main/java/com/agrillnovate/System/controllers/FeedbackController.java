package com.agrillnovate.System.controllers;

import com.agrillnovate.System.dto.CommentDTO;
import com.agrillnovate.System.dto.CommentStat;
import com.agrillnovate.System.dto.FeedbackDTO;
import com.agrillnovate.System.model.Comment;
import com.agrillnovate.System.model.Feedback;
import com.agrillnovate.System.service.CommentService;
import com.agrillnovate.System.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/feedback")
    public Feedback createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        return feedbackService.createFeedback(feedbackDTO.getResearchID(), feedbackDTO);
    }

    @PostMapping("/comments")
    public Comment createComment(@RequestBody CommentDTO commentDTO) {
        return commentService.createComment(commentDTO);
    }

    @GetMapping("/comments/{researchID}")
    public ResponseEntity<List<Map<String, Object>>> getCommentsByResearchID(@PathVariable Long researchID) {
        logger.debug("Fetching comments for research ID: {}", researchID);
        List<Comment> comments = commentService.getCommentsByResearchID(researchID);
        List<Map<String, Object>> simplifiedComments = comments.stream().map(comment -> {
            Map<String, Object> simplifiedComment = new HashMap<>();
            simplifiedComment.put("commentID", comment.getCommentID());
            simplifiedComment.put("content", comment.getContent());
            simplifiedComment.put("name", comment.getName());
            simplifiedComment.put("email", comment.getEmail());
            simplifiedComment.put("phone", comment.getPhone());
            simplifiedComment.put("dateSubmitted", comment.getDateSubmitted());
            return simplifiedComment;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(simplifiedComments);
    }





}
