package org.example.repo.databaseRepo;

import org.example.database.DBConnection;
import org.example.model.Company;
import org.example.model.Market;
import org.example.model.User;
import org.example.repo.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarketRepositoryDB implements IRepository<Market> {

    private Connection connection = DBConnection.getConnection();

    private static MarketRepositoryDB instance;

    private List<Market> cache;

    public static MarketRepositoryDB getInstance() {
        if(instance == null) {
            instance = new MarketRepositoryDB();
        }
        return instance;
    }

    public MarketRepositoryDB() {
        this.cache = importCache();
    }

    private List<Market> importCache(){
        List<Market> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Markets";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("marketId");
                String name = results.getString("name");
                String location = results.getString("location");
                result.add(new Market(id,name,location));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public Market searchIdCache(int id) {
        for(Market market: this.cache){
            if (market.getId() == id){
                return market;
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

    @Override
    public List<Market> getObjects() {
        return this.cache;
    }

    @Override
    public void save(Market market) {
        int id = market.getId();
        String name = market.getName();
        String location = market.getLocation();
        String query = "INSERT INTO Markets (marketId, name, location) VALUES (?, ?, ?)";


        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, location);
            statement.executeUpdate();

            this.cache.add(market);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Market entity) {
        int id = entity.getId();
        String name = entity.getName();
        String location = entity.getLocation();

        Market old_market = searchIdCache(id);

        if(old_market !=null) {
            try {
                String query = "UPDATE Markets SET name = ?, location = ? WHERE marketId = ?;";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setString(2, location);
                statement.setInt(3, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            old_market.setName(name);
            old_market.setLocation(location);
        }

    }

    @Override
    public void delete(Market object) {
        int id = object.getId();
        String query = "DELETE FROM Markets WHERE marketId = ?";
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
