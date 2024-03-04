package org.example.repo.databaseRepo;

import org.example.database.DBConnection;
import org.example.model.Admin;
import org.example.model.Company;
import org.example.model.GrowthStock;
import org.example.model.Market;
import org.example.repo.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrowthStockRepositoryDB implements IRepository<GrowthStock> {
    private final Connection connection = DBConnection.getConnection();

    private static GrowthStockRepositoryDB instance;
    private MarketRepositoryDB marketRepositoryDB;
    private CompanyRepositoryDB companyRepositoryDB;
    private List<GrowthStock> cache;

    public GrowthStockRepositoryDB() {
        this.marketRepositoryDB = MarketRepositoryDB.getInstance();
        this.companyRepositoryDB = CompanyRepositoryDB.getInstance();
        this.cache = importCache();
    }



    public static GrowthStockRepositoryDB getInstance() {
        if(instance == null) {
            instance = new GrowthStockRepositoryDB();
        }
        return instance;
    }


    private List<GrowthStock> importCache(){
        List<GrowthStock> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM GrowthStocks";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                int id = results.getInt("companyId");
                String name = results.getString("name");
                Company company = this.companyRepositoryDB.searchIdCache(results.getInt("companyId"));
                Market market = this.marketRepositoryDB.searchIdCache(results.getInt("marketId"));
                int growth_rate = results.getInt("growth_rate");
                result.add(new GrowthStock(id,name,company,market,growth_rate));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public GrowthStock searchIdCache(int id) {
        for(GrowthStock growthStock: this.cache){
            if (growthStock.getId() == id){
                return growthStock;
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
    public List<GrowthStock> getObjects() {
        return this.cache;
    }



    @Override
    public void save(GrowthStock growthStock) {
        int id = growthStock.getId();
        String name = growthStock.getName();
        int company = growthStock.getCompany().getId();
        int market = growthStock.getMarket().getId();
        int growth_rate = growthStock.getGrowth_rate();
        String query = "INSERT INTO GrowthStocks (growthStockId, name, companyId, marketId, growth_rate) VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, company);
            statement.setInt(4, market);
            statement.setInt(5, growth_rate);
            statement.executeUpdate();

            this.cache.add(growthStock);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GrowthStock entity) {
        int id = entity.getId();
        String name = entity.getName();
        int company = entity.getCompany().getId();
        int market = entity.getMarket().getId();
        int growth_rate = entity.getGrowth_rate();

        GrowthStock old_growthStock = searchIdCache(id);

        if(old_growthStock !=null) {
            try {
                String query = "UPDATE GrowthStocks SET name = ?, companyId = ?, marketId = ?, growth_rate = ? WHERE growthStockId = ?;";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(5, id);
                statement.setString(1, name);
                statement.setInt(2, company);
                statement.setInt(3, market);
                statement.setInt(4, growth_rate);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            old_growthStock.setName(name);
            old_growthStock.setCompany(entity.getCompany());
            old_growthStock.setMarket(entity.getMarket());
            old_growthStock.setGrowth_rate(growth_rate);
        }

    }

    @Override
    public void delete(GrowthStock object) {
        int id = object.getId();
        String query = "DELETE FROM GrowthStocks WHERE growthStockId = ?";
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
