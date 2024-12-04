package com.agrillnovate.System.Events;

import com.agrillnovate.System.model.Research;
import org.springframework.context.ApplicationEvent;

public class NewResearchEvent extends ApplicationEvent {
    private final Research research;

    public NewResearchEvent(Object source, Research research) {
        super(source);
        this.research = research;
    }

    public Research getResearch() {
        return research;
    }
}
