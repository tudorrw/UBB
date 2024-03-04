package org.example.repo;

import org.example.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository implements IRepository<Notification> {
    private List<Notification> notifications;

    public NotificationRepository() {
        this.notifications = new ArrayList<>();
    }

    @Override
    public List<Notification> getObjects() {
        return notifications;
    }

    @Override
    public void save(Notification entity) {
        notifications.add(entity);
    }

    @Override
    public void delete(Notification object) {
        notifications.remove(object);
    }
}
