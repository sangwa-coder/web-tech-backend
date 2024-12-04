package com.agrillnovate.System.service;

import com.agrillnovate.System.dto.CommentDTO;
import com.agrillnovate.System.dto.CommentStat;
import com.agrillnovate.System.model.Comment;
import com.agrillnovate.System.model.Research;
import com.agrillnovate.System.Repository.CommentRepository;
import com.agrillnovate.System.Repository.ResearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ResearchRepository researchRepository;

    public Comment createComment(CommentDTO commentDTO) {
        Research research = researchRepository.findById(commentDTO.getResearchID())
                .orElseThrow(() -> new RuntimeException("Research not found"));
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setEmail(commentDTO.getEmail());
        comment.setPhone(commentDTO.getPhone());
        comment.setName(commentDTO.getName());
        comment.setDateSubmitted(commentDTO.getDateSubmitted());
        comment.setResearch(research);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByResearchID(Long researchID) {
        return commentRepository.findByResearchResearchID(researchID);
    }

    public List<CommentStat> getCommentsStats() {
        return commentRepository.getCommentsStats();
    }

    public List<CommentStat> getCommentStats() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .collect(Collectors.groupingBy(comment -> comment.getResearch().getTitle()))
                .entrySet().stream()
                .map(entry -> new CommentStat(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toList());
    }
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    public long countComments() {
        return commentRepository.count();
    }
}
