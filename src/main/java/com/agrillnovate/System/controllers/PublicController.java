package com.agrillnovate.System.controllers;

import com.agrillnovate.System.dto.InfographicDTO;
import com.agrillnovate.System.dto.PublicKnowledgeDTO;
import com.agrillnovate.System.dto.ResearchDTO;
import com.agrillnovate.System.service.InfographicService;
import com.agrillnovate.System.service.PublicKnowledgeService;
import com.agrillnovate.System.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/research")
public class PublicController {

    @Autowired
    private ResearchService researchService;
    @Autowired
    private InfographicService infographicService;

    @Autowired
    private PublicKnowledgeService publicKnowledgeService;


    @GetMapping
    public List<ResearchDTO> getAllResearch() {
        return researchService.getAllResearch();
    }

    @GetMapping("/{id}")
    public ResearchDTO getResearchById(@PathVariable Long id) {
        return researchService.getResearchById(id);
    }
    @GetMapping("/infographics")
    public List<InfographicDTO> getAllInfographics() {
        return infographicService.getAllInfographics();
    }

    @GetMapping("/infographics/{id}")
    public InfographicDTO getInfographicById(@PathVariable Long id) {
        return infographicService.getInfographicById(id);
    }

    // Public Knowledge endpoints
    @GetMapping("/public-knowledge")
    public List<PublicKnowledgeDTO> getAllPublicKnowledge() {
        return publicKnowledgeService.getAllPublicKnowledge();
    }

    @GetMapping("/public-knowledge/{id}")
    public PublicKnowledgeDTO getPublicKnowledgeById(@PathVariable Long id) {
        return publicKnowledgeService.getPublicKnowledgeById(id);
    }

}
