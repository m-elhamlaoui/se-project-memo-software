package com.example.taskblock.model.notification;


public enum NotificationType {
    NEW_VOTE("New vote on your task"),
    TASK_ACCEPTED("Task has been accepted"),
    TASK_REJECTED("Task has been rejected");

    private final String defaultMessage;

    public String getDefaultMessage() {
        return defaultMessage;
    }

    NotificationType(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

}