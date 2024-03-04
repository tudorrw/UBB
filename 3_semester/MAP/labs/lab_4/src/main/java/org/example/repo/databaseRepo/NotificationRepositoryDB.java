package org.example.repo.databaseRepo;

import org.example.database.DBConnection;
import org.example.model.Admin;
import org.example.model.Notification;
import org.example.model.User;
import org.example.repo.IRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepositoryDB implements IRepository<Notification> {
    private Connection connection = DBConnection.getConnection();

    private static NotificationRepositoryDB instance;
    private UserRepositoryDB userRepositoryDB;

    private List<Notification> cache;

    public NotificationRepositoryDB() {
        this.userRepositoryDB = UserRepositoryDB.getInstance();
        this.cache = importCache();
    }

    private List<Notification> importCache(){
        List<Notification> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Notifications";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("notificationId");
                User user = userRepositoryDB.searchIdCache(results.getInt("userId"));
                String message = results.getString("message");
                LocalDateTime timestamp = results.getTimestamp("timestamp").toLocalDateTime();
                String status = results.getString("status");

                result.add(new Notification(id,user,message,timestamp,status));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public Notification searchIdCache(int id) {
        for(Notification notification: this.cache){
            if (notification.getId() == id){
                return notification;
            }
        }

        return null;
    }

    public int searchIdCachePosition(int id) {
        for(int i = 0 ;i < this.cache.size(); i++){
            if (cache.get(i).getId() == id){
                return i;
            }
        }

        return -1;
    }

    public static NotificationRepositoryDB getInstance() {
        if(instance == null) {
            instance = new NotificationRepositoryDB();
        }
        return instance;
    }

    @Override
    public List<Notification> getObjects() {
        return this.cache;
    }

    @Override
    public void save(Notification notification) {
        int id = notification.getId();
        int userId = notification.getUser().getId();
        String message = notification.getMessage();
        Timestamp timestamp = Timestamp.valueOf(notification.getTimestamp());
        String status = notification.getStatus();
        String query = "INSERT INTO Notifications (notificationId, userId, message, timestamp, status) VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, userId);
            statement.setString(3, message);
            statement.setTimestamp(4, timestamp);
            statement.setString(5, status);

            statement.executeUpdate();

            this.cache.add(notification);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Notification notification) {
        int id = notification.getId();
        int userId = notification.getUser().getId();
        String message = notification.getMessage();
        Timestamp timestamp = Timestamp.valueOf(notification.getTimestamp());
        String status = notification.getStatus();

        Notification old_notification = searchIdCache(id);

        if(old_notification != null) {
            try {
                String query = "UPDATE Users SET userId = ?, message = ?, timestamp = ?, status = ? WHERE notificationId = ?;";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(5, id);
                statement.setInt(1, userId);
                statement.setString(2, message);
                statement.setTimestamp(3, timestamp);
                statement.setString(4, status);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            old_notification.setUser(notification.getUser());
            old_notification.setMessage(message);
            old_notification.setTimestamp(notification.getTimestamp());
            old_notification.setStatus(status);
        }

    }

    @Override
    public void delete(Notification object) {
        int id = object.getId();
        String query = "DELETE FROM Users WHERE notificationId = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            this.cache.remove(searchIdCachePosition(id));

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
