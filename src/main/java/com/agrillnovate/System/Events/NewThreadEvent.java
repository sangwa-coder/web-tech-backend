package com.agrillnovate.System.Events;

import com.agrillnovate.System.dto.ThreadDTO;
import org.springframework.context.ApplicationEvent;

public class NewThreadEvent extends ApplicationEvent {
    private final ThreadDTO threadDTO;

    public NewThreadEvent(Object source, ThreadDTO threadDTO) {
        super(source);
        this.threadDTO = threadDTO;
    }

    public ThreadDTO getThreadDTO() {
        return threadDTO;
    }
}
