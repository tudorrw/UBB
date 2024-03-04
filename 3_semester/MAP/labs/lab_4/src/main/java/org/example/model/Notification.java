package org.example.model;

import java.time.LocalDateTime;

public class Notification {
    private int id;
    private User user;
    private String message;
    private LocalDateTime timestamp;
    private String status;

    public Notification(int id, User user, String message, LocalDateTime timestamp, String status) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
