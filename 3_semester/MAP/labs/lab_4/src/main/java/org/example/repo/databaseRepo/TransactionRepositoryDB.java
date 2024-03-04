package org.example.repo.databaseRepo;

import org.example.database.DBConnection;
import org.example.model.Notification;
import org.example.model.Stock;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.repo.IRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryDB implements IRepository<Transaction> {
    private Connection connection = DBConnection.getConnection();

    private static TransactionRepositoryDB instance;
    private UserRepositoryDB userRepositoryDB;
    private GrowthStockRepositoryDB growthStockRepositoryDB;
    private ValueStockRepositoryDB valueStockRepositoryDB;

    private List<Transaction> cache;

    public TransactionRepositoryDB() {
        this.userRepositoryDB = UserRepositoryDB.getInstance();
        this.growthStockRepositoryDB = GrowthStockRepositoryDB.getInstance();
        this.valueStockRepositoryDB = ValueStockRepositoryDB.getInstance();
        this.cache = importCache();
    }

    private List<Transaction> importCache(){
        List<Transaction> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM ValueStockTransactions";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("transactionId");
                User user = userRepositoryDB.searchIdCache(results.getInt("userId"));
                Stock stock = valueStockRepositoryDB.searchIdCache(results.getInt("valueStockId"));
                LocalDateTime date = results.getTimestamp("date").toLocalDateTime();

                result.add(new Transaction(id,user,stock,date));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String query = "SELECT * FROM GrowthStockTransactions";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("transactionId");
                User user = userRepositoryDB.searchIdCache(results.getInt("userId"));
                Stock stock = valueStockRepositoryDB.searchIdCache(results.getInt("growthStockId"));
                LocalDateTime date = results.getTimestamp("date").toLocalDateTime();

                result.add(new Transaction(id,user,stock,date));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }



        return result;
    }


    public Transaction searchIdCache(int id) {
        for(Transaction transaction: this.cache){
            if (transaction.getId() == id){
                return transaction;
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

    public static TransactionRepositoryDB getInstance() {
        if(instance == null) {
            instance = new TransactionRepositoryDB();
        }
        return instance;
    }

    @Override
    public List<Transaction> getObjects() {
        return this.cache;
    }

    @Override
    public void save(Transaction transaction) {
//        int id = transaction.getId();
//        int userId = transaction.getUser().getId();
//        String message = transaction.getMessage();
//        Timestamp timestamp = Timestamp.valueOf(transaction.getTimestamp());
//        String status = transaction.getStatus();
//        String query = "INSERT INTO Notifications (notificationId, userId, message, timestamp, status) VALUES (?, ?, ?, ?, ?)";
//        try{
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setInt(1, id);
//            statement.setInt(2, userId);
//            statement.setString(3, message);
//            statement.setTimestamp(4, timestamp);
//            statement.setString(5, status);
//
//            statement.executeUpdate();
//
//            this.cache.add(transaction);
//
//        }
//        catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void update(Transaction transaction) {
//        int id = transaction.getId();
//        int userId = transaction.getUser().getId();
//        String message = transaction.getMessage();
//        Timestamp timestamp = Timestamp.valueOf(transaction.getTimestamp());
//        String status = transaction.getStatus();
//
//        Notification old_notification = searchIdCache(id);
//
//        if(old_notification != null) {
//            try {
//                String query = "UPDATE Users SET userId = ?, message = ?, timestamp = ?, status = ? WHERE notificationId = ?;";
//                PreparedStatement statement = connection.prepareStatement(query);
//                statement.setInt(1, id);
//                statement.setInt(2, userId);
//                statement.setString(3, message);
//                statement.setTimestamp(4, timestamp);
//                statement.setString(5, status);
//                statement.executeUpdate();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            old_notification.setUser(transaction.getUser());
//            old_notification.setMessage(message);
//            old_notification.setTimestamp(transaction.getTimestamp());
//            old_notification.setStatus(status);
//        }

    }

    @Override
    public void delete(Transaction object) {
//        int id = object.getId();
//        String query = "DELETE FROM Users WHERE transactionId = ?";
//        try{
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setInt(1, id);
//            statement.executeUpdate();
//
//            this.cache.remove(searchIdCachePosition(id));
//
//        }
//        catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
