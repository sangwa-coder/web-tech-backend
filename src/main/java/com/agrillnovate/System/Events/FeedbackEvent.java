package com.agrillnovate.System.Events;

import com.agrillnovate.System.model.Feedback;
import org.springframework.context.ApplicationEvent;

public class FeedbackEvent extends ApplicationEvent {
    private final Feedback feedback;

    public FeedbackEvent(Object source, Feedback feedback) {
        super(source);
        this.feedback = feedback;
    }

    public Feedback getFeedback() {
        return feedback;
    }
}