package org.example.repo.inMemoryRepo;

import org.example.model.Notification;
import org.example.repo.IRepository;

import java.util.ArrayList;

public class NotificationRepositoryIM implements IRepository<Notification> {
    private ArrayList<Notification> notifications;
    private static NotificationRepositoryIM instance;

    private NotificationRepositoryIM() {
        this.notifications = new ArrayList<>();
    }

    @Override
    public ArrayList<Notification> getObjects() {
        return notifications;
    }

    @Override
    public void save(Notification entity) {
        notifications.add(entity);
    }

    @Override
    public void update(Notification entity) {

    }

    @Override
    public void delete(Notification object) {
        notifications.remove(object);
    }


    public static NotificationRepositoryIM getInstance() {
        if (instance == null) {
            instance = new NotificationRepositoryIM();
        }
        return instance;
    }
}
