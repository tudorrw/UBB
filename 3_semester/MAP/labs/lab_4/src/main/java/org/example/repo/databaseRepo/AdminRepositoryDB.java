package org.example.repo.databaseRepo;

import org.example.database.DBConnection;
import org.example.model.Admin;
import org.example.model.AdminRole;
import org.example.model.Company;
import org.example.model.User;
import org.example.repo.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryDB implements IRepository<Admin> {
    private Connection connection = DBConnection.getConnection();

    private static AdminRepositoryDB instance;

    private List<Admin> cache;

    public AdminRepositoryDB() {
         this.cache = importCache();
    }

    private List<Admin> importCache(){
        List<Admin> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Admins";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("adminId");
                String username = results.getString("username");
                String password = results.getString("password");
                String adminRole = results.getString("admin_role");
                result.add(new Admin(id,username,password,adminRole));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public Admin searchIdCache(int id) {
        for(Admin admin: this.cache){
            if (admin.getId() == id){
                return admin;
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

    public static AdminRepositoryDB getInstance() {
        if(instance == null) {
            instance = new AdminRepositoryDB();
        }
        return instance;
    }

    @Override
    public List<Admin> getObjects() {
        return this.cache;
    }

    @Override
    public void save(Admin admin) {
        int adminId = admin.getId();
        String username = admin.getUsername();
        String password = admin.getPassword();
        String admin_role = admin.getAdminRole();
        String query = "INSERT INTO Admins (adminId, username, password, admin_role) VALUES (?, ?, ?, ?)";


        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, adminId);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, admin_role);
            statement.executeUpdate();

            this.cache.add(admin);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Admin entity) {
        int id = entity.getId();
        String username = entity.getUsername();
        String password = entity.getPassword();
        String adminRole = entity.getAdminRole();

        Admin old_admin = searchIdCache(id);

        if(old_admin != null){
            try {
                String query = "UPDATE Admins SET username = ?, password = ?, adminRole = ? WHERE id = ?;";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, adminRole);
                statement.setInt(4, id);
                statement.executeUpdate();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }

            old_admin.setUsername(username);
            old_admin.setPassword(password);
            old_admin.setAdminRole(adminRole);
        }


    }

    @Override
    public void delete(Admin object) {
        int id = object.getId();
        String query = "DELETE FROM Admins WHERE id = ?";

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
