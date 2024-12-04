package com.agrillnovate.System.service;

import com.agrillnovate.System.dto.CommentDTO;
import com.agrillnovate.System.dto.FeedbackDTO;
import com.agrillnovate.System.dto.ReportFeedbackDTO;
import com.agrillnovate.System.model.Comment;
import com.agrillnovate.System.model.Feedback;
import com.agrillnovate.System.Repository.CommentRepository;
import com.agrillnovate.System.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Map<String, Object>> getFeedbackCountPerDay() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream()
                .collect(Collectors.groupingBy(f -> convertToLocalDate(f.getDateSubmitted()), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("date", entry.getKey());
                    result.put("count", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getCommentCountPerDay() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .collect(Collectors.groupingBy(c -> convertToLocalDate(c.getDateSubmitted()), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("date", entry.getKey());
                    result.put("count", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }


    public List<ReportFeedbackDTO> getFeedbacksByDate(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        Date startOfDay = Date.from(date.atStartOfDay(zoneId).toInstant());
        Date endOfDay = Date.from(date.plusDays(1).atStartOfDay(zoneId).toInstant());

        return feedbackRepository.findFeedbacksByDate(startOfDay, endOfDay);
    }


    public List<CommentDTO> getCommentsByDate(LocalDate date) {
        Date startOfDay = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return commentRepository.findCommentsByDate(startOfDay, endOfDay);
    }

    private LocalDate convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
