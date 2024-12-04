package com.agrillnovate.System.Events;

import com.agrillnovate.System.model.User;
import org.springframework.context.ApplicationEvent;

public class UserSignupEvent extends ApplicationEvent {
    private final User user;

    public UserSignupEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}