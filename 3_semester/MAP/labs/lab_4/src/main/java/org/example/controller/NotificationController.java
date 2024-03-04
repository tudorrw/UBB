package org.example.controller;


import org.example.model.Notification;
import org.example.model.User;
import org.example.repo.IRepository;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationController {
    private IRepository<Notification> repository;

    public NotificationController(IRepository<Notification> repository) {
        this.repository = repository;
    }

    public boolean addNotification(int id, User user, String message, LocalDateTime timestamp, String status){
        if(!this.searchNotificationBool(id)) {
            repository.save(new Notification(id,user, message, timestamp,status));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeNotification(int id){
        Notification search = this.searchNotification(id);
        if(search != null) {
            repository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Notification> getAll(){
        return repository.getObjects();
    }

    public boolean searchNotificationBool(int id){
        List<Notification> notifications = repository.getObjects();
        for (Notification notification: notifications) {
            if(notification.getId() == id){
                return true;
            }
        }
        return false;
    }

    public Notification searchNotification(int id){
        List<Notification> notifications = repository.getObjects();
        for (Notification notification: notifications) {
            if(notification.getId() == id){
                return notification;
            }
        }
        return null;
    }
}
