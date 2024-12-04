package com.agrillnovate.System.service;

import com.agrillnovate.System.dto.DTOConverter;
import com.agrillnovate.System.dto.PublicKnowledgeDTO;
import com.agrillnovate.System.model.PublicKnowledge;
import com.agrillnovate.System.Repository.PublicKnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicKnowledgeService {

    @Autowired
    private PublicKnowledgeRepository publicKnowledgeRepository;

    public List<PublicKnowledgeDTO> getAllPublicKnowledge() {
        return publicKnowledgeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PublicKnowledgeDTO createPublicKnowledge(PublicKnowledgeDTO publicKnowledgeDTO) {
        PublicKnowledge publicKnowledge = new PublicKnowledge();
        publicKnowledge.setTitle(publicKnowledgeDTO.getTitle());
        publicKnowledge.setContent(publicKnowledgeDTO.getContent());
        publicKnowledge.setImage(publicKnowledgeDTO.getImage());
        publicKnowledge.setDatePublished(publicKnowledgeDTO.getDatePublished());
        PublicKnowledge savedPublicKnowledge = publicKnowledgeRepository.save(publicKnowledge);
        return convertToDTO(savedPublicKnowledge);
    }

    public PublicKnowledgeDTO updatePublicKnowledge(Long id, PublicKnowledgeDTO publicKnowledgeDTO) {
        PublicKnowledge publicKnowledge = publicKnowledgeRepository.findById(id).orElseThrow(() -> new RuntimeException("Public knowledge not found"));
        publicKnowledge.setTitle(publicKnowledgeDTO.getTitle());
        publicKnowledge.setContent(publicKnowledgeDTO.getContent());
        publicKnowledge.setImage(publicKnowledgeDTO.getImage());
        publicKnowledge.setDatePublished(publicKnowledgeDTO.getDatePublished());
        PublicKnowledge updatedPublicKnowledge = publicKnowledgeRepository.save(publicKnowledge);
        return convertToDTO(updatedPublicKnowledge);
    }

    public void deletePublicKnowledge(Long id) {
        publicKnowledgeRepository.deleteById(id);
    }

    private PublicKnowledgeDTO convertToDTO(PublicKnowledge publicKnowledge) {
        return new PublicKnowledgeDTO(
                publicKnowledge.getId(),
                publicKnowledge.getTitle(),
                publicKnowledge.getContent(),
                publicKnowledge.getImage(),
                publicKnowledge.getDatePublished()
        );
    }


    public PublicKnowledgeDTO getPublicKnowledgeById(Long id) {
        PublicKnowledge publicKnowledge = publicKnowledgeRepository.findById(id).orElseThrow();
        return DTOConverter.convertToPublicKnowledgeDTO(publicKnowledge);
    }
}
