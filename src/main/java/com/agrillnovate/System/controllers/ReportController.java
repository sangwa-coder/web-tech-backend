package com.agrillnovate.System.controllers;

import com.agrillnovate.System.dto.CommentDTO;
import com.agrillnovate.System.dto.FeedbackDTO;
import com.agrillnovate.System.dto.ReportFeedbackDTO;
import com.agrillnovate.System.model.Comment;
import com.agrillnovate.System.model.Feedback;
import com.agrillnovate.System.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/feedback-per-day")
    public ResponseEntity<List<Map<String, Object>>> getFeedbackCountPerDay() {
        List<Map<String, Object>> feedbackCountPerDay = reportService.getFeedbackCountPerDay();
        return ResponseEntity.ok(feedbackCountPerDay);
    }

    @GetMapping("/comments-per-day")
    public ResponseEntity<List<Map<String, Object>>> getCommentCountPerDay() {
        List<Map<String, Object>> commentCountPerDay = reportService.getCommentCountPerDay();
        return ResponseEntity.ok(commentCountPerDay);
    }



    @GetMapping("/feedback-by-date")
    public ResponseEntity<List<ReportFeedbackDTO>> getFeedbacksByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ReportFeedbackDTO> feedbacks = reportService.getFeedbacksByDate(date);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/comments-by-date")
    public ResponseEntity<List<CommentDTO>> getCommentsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<CommentDTO> comments = reportService.getCommentsByDate(date);
        return ResponseEntity.ok(comments);
    }
}
