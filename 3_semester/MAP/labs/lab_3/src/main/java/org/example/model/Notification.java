package org.example.model;

import java.time.LocalDateTime;

public class Notification {
    private int userId;
    private int id;
    private String message;
    private LocalDateTime timestamp;
    private String status;

    public Notification(int user, int id, String message, LocalDateTime timestamp, String status) {
        this.userId = user;
        this.id = id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
