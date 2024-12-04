package com.agrillnovate.System.service;

import com.agrillnovate.System.Repository.CommentRepository;
import com.agrillnovate.System.Repository.FeedbackRepository;
import com.agrillnovate.System.Repository.ResearchImageRepository;
import com.agrillnovate.System.Repository.ResearchRepository;
import com.agrillnovate.System.dto.CommentDTO;
import com.agrillnovate.System.dto.FeedbackDTO;
import com.agrillnovate.System.dto.ResearchDTO;
import com.agrillnovate.System.dto.ResearchImageDTO;
import com.agrillnovate.System.model.Comment;
import com.agrillnovate.System.model.Feedback;
import com.agrillnovate.System.model.Research;
import com.agrillnovate.System.model.ResearchImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResearchService {

    @Autowired
    private ResearchRepository researchRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;


    @Autowired
    private ResearchImageRepository researchImageRepository;

    public Research saveResearch(Research research, List<MultipartFile> images) throws IOException {
        Research savedResearch = researchRepository.save(research);

        List<ResearchImage> researchImages = new ArrayList<>();
        for (MultipartFile image : images) {
            ResearchImage researchImage = new ResearchImage(image.getBytes(), savedResearch);
            researchImages.add(researchImage);
        }
        researchImageRepository.saveAll(researchImages);

        return savedResearch;
    }

    public ResearchDTO getResearchById(Long id) {
        Research research = researchRepository.findById(id).orElse(null);
        return research != null ? convertToDTO(research) : null;
    }


    public List<ResearchDTO> getAllResearch() {
        List<Research> researches = researchRepository.findAll();
        return researches.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ResearchDTO convertToDTO(Research research) {
        List<ResearchImageDTO> images = research.getImages().stream()
                .map(image -> new ResearchImageDTO(image.getId(), image.getImage()))
                .collect(Collectors.toList());

        List<FeedbackDTO> feedbacks = research.getFeedbacks().stream()
                .map(feedback -> new FeedbackDTO(feedback.getFeedbackID(), feedback.getContent(), feedback.getDateSubmitted(), feedback.getResearch().getResearchID()))
                .collect(Collectors.toList());

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
                images,
                feedbacks
        );
    }

    public List<Research> getResearchByUserId(Long userId) {
        return researchRepository.findByUserUserID(userId);
    }

    public List<Research> findResearchByAuthor(String author) {
        return researchRepository.findByAuthor(author);
    }



    public Research updateResearch(Long id, Research researchDetails, List<MultipartFile> images) throws IOException {
        Research research = researchRepository.findById(id).orElse(null);
        if (research != null) {
            // Update simple fields
            if (researchDetails.getTitle() != null) research.setTitle(researchDetails.getTitle());
            if (researchDetails.getAuthor() != null) research.setAuthor(researchDetails.getAuthor());
            if (researchDetails.getDatePublished() != null) research.setDatePublished(researchDetails.getDatePublished());
            if (researchDetails.getContent() != null) research.setContent(researchDetails.getContent());
            if (researchDetails.getLatitude() != null) research.setLatitude(researchDetails.getLatitude());
            if (researchDetails.getLongitude() != null) research.setLongitude(researchDetails.getLongitude());
            if (researchDetails.getCategory() != null) research.setCategory(researchDetails.getCategory());

            // Modify the existing list of images only if new images are provided
            if (images != null && !images.isEmpty()) {
                // Remove existing images from the list (or you could mark them for deletion if necessary)
                List<ResearchImage> currentImages = research.getImages();
                currentImages.clear();

                // Add new images to the existing list
                for (MultipartFile image : images) {
                    ResearchImage researchImage = new ResearchImage(image.getBytes(), research);
                    currentImages.add(researchImage);
                }
                // No need to set the collection back; just modify it in place
            }

            return researchRepository.save(research);
        }
        return null;
    }


    public void deleteResearch(Long id) {
        researchRepository.deleteById(id);
    }

    public List<CommentDTO> getCommentsByResearchID(Long researchID) {
        List<Comment> comments = commentRepository.findByResearch_ResearchID(researchID);
        return comments.stream()
                .map(comment -> new CommentDTO(
                        comment.getResearch().getResearchID(),
                        comment.getName(),
                        comment.getContent(),
                        comment.getEmail(),
                        comment.getPhone(),
                        comment.getDateSubmitted()))
                .collect(Collectors.toList());
    }

    public List<FeedbackDTO> getFeedbackByResearchID(Long researchID) {
        List<Feedback> feedbacks = feedbackRepository.findByResearch_ResearchID(researchID);
        return feedbacks.stream()
                .map(feedback -> new FeedbackDTO(
                        feedback.getFeedbackID(),
                        feedback.getContent(),
                        feedback.getDateSubmitted(),
                        feedback.getResearch().getResearchID()))
                .collect(Collectors.toList());
    }
    public List<String> getDistinctCategories() {
        return researchRepository.findAll().stream()
                .map(Research::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
    public long countResearch() {
        return researchRepository.count();
    }
}
