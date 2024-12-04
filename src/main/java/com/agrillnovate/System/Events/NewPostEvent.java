package com.agrillnovate.System.Events;

import com.agrillnovate.System.dto.PostDTO;
import org.springframework.context.ApplicationEvent;

public class NewPostEvent extends ApplicationEvent {
    private final PostDTO postDTO;

    public NewPostEvent(Object source, PostDTO postDTO) {
        super(source);
        this.postDTO = postDTO;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }
}
