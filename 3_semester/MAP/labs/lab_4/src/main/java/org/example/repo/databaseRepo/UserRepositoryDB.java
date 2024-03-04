package org.example.repo.databaseRepo;

import org.example.database.DBConnection;
import org.example.model.Admin;
import org.example.model.User;
import org.example.repo.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDB implements IRepository<User> {

    private Connection connection = DBConnection.getConnection();

    private static UserRepositoryDB instance;

    private List<User> cache;

    public UserRepositoryDB() {
        this.cache = importCache();
    }

    private List<User> importCache(){
        List<User> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("userId");
                String username = results.getString("username");
                String password = results.getString("password");
                result.add(new User(id,username,password));
            }
        }
        catch (SQLException e) {
        }

        return result;
    }


    public User searchIdCache(int id) {
        for(User user: this.cache){
            if (user.getId() == id){
                return user;
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

    public static UserRepositoryDB getInstance() {
        if(instance == null) {
            instance = new UserRepositoryDB();
        }
        return instance;
    }

    @Override
    public List<User> getObjects() {
        return this.cache;
    }

    @Override
    public void save(User user) {
        int id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        String query = "INSERT INTO Users (userId, username, password) VALUES (?, ?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();

            this.cache.add(user);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity) {
        int id = entity.getId();
        String username = entity.getUsername();
        String password = entity.getPassword();

        User old_user = searchIdCache(id);

        if(old_user != null) {
            try {
                String query = "UPDATE Users SET username = ?, password = ? WHERE userId = ?;";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setInt(3, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            old_user.setUsername(username);
            old_user.setPassword(password);
        }

    }

    @Override
    public void delete(User object) {
        int id = object.getId();
        String query = "DELETE FROM Users WHERE userId = ?";
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
