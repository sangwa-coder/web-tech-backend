package com.agrillnovate.System.service;

import com.agrillnovate.System.dto.DTOConverter;
import com.agrillnovate.System.dto.InfographicDTO;
import com.agrillnovate.System.model.Infographic;
import com.agrillnovate.System.Repository.InfographicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfographicService {

    @Autowired
    private InfographicRepository infographicRepository;

    public List<InfographicDTO> getAllInfographics() {
        return infographicRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InfographicDTO createInfographic(InfographicDTO infographicDTO) {
        Infographic infographic = new Infographic();
        infographic.setTitle(infographicDTO.getTitle());
        infographic.setDescription(infographicDTO.getDescription());
        infographic.setImage(infographicDTO.getImage());
        Infographic savedInfographic = infographicRepository.save(infographic);
        return convertToDTO(savedInfographic);
    }

    public InfographicDTO updateInfographic(Long id, InfographicDTO infographicDTO) {
        Infographic infographic = infographicRepository.findById(id).orElseThrow(() -> new RuntimeException("Infographic not found"));
        infographic.setTitle(infographicDTO.getTitle());
        infographic.setDescription(infographicDTO.getDescription());
        infographic.setImage(infographicDTO.getImage());
        Infographic updatedInfographic = infographicRepository.save(infographic);
        return convertToDTO(updatedInfographic);
    }

    public void deleteInfographic(Long id) {
        infographicRepository.deleteById(id);
    }

    private InfographicDTO convertToDTO(Infographic infographic) {
        return new InfographicDTO(
                infographic.getId(),
                infographic.getTitle(),
                infographic.getDescription(),
                infographic.getImage()
        );
    }


    public InfographicDTO getInfographicById(Long id) {
        Infographic infographic = infographicRepository.findById(id).orElseThrow();
        return DTOConverter.convertToInfographicDTO(infographic);
    }
}
