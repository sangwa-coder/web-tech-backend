package com.agrillnovate.System.dto;

import com.agrillnovate.System.model.*;
import com.agrillnovate.System.model.Thread;

public class DTOConverter {

    public static ThreadDTO convertToThreadDTO(Thread thread) {
        ThreadDTO threadDTO = new ThreadDTO();
        threadDTO.setThreadId(thread.getThreadId());
        threadDTO.setTitle(thread.getTitle());
        threadDTO.setCreated_at(thread.getCreated_at());
        threadDTO.setUserId(thread.getUser().getUserID());
        threadDTO.setEmail(thread.getUser().getEmail());
        return threadDTO;
    }

    public static PostDTO convertToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(post.getPostId());
        postDTO.setContent(post.getContent());
        postDTO.setCreated_at(post.getCreated_at());
        postDTO.setUserId(post.getUser().getUserID());
        postDTO.setEmail(post.getUser().getEmail());
        postDTO.setThreadId(post.getThread().getThreadId());
        return postDTO;
    }

    public static InfographicDTO convertToInfographicDTO(Infographic infographic) {
        return new InfographicDTO(
                infographic.getId(),
                infographic.getTitle(),
                infographic.getDescription(),
                infographic.getImage()
        );
    }

    public static PublicKnowledgeDTO convertToPublicKnowledgeDTO(PublicKnowledge publicKnowledge) {
        return new PublicKnowledgeDTO(
                publicKnowledge.getId(),
                publicKnowledge.getTitle(),
                publicKnowledge.getContent(),
                publicKnowledge.getImage(),
                publicKnowledge.getDatePublished()
        );
    }
}
