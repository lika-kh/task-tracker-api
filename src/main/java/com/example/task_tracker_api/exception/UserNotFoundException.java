package com.example.task_tracker_api.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("User not found " + email);
    }

}